package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jeanpacheco.syncraestateai_mobile.ui.theme.SyncraGreen
import androidx.core.net.toUri

@Composable
fun LoginScreen(navController: NavController) {
    // --- 1. MEMORIA DE LA PANTALLA (ESTADOS) ---
    // Aquí guardamos lo que el usuario va tecleando.
    // Si cambian, Compose redibuja la pantalla automáticamente.
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Interruptor para ver la contraseña (true = se ve, false = puntitos)
    var passwordVisible by remember { mutableStateOf(false) }

    // Interruptor para mostrar el banner de error simulado
    var showError by remember { mutableStateOf(false) }

    // --- 2. PALETA DE COLORES ---
    val SyncraDarkBlue = Color(0xFF1F4C6B) // El azul oscuro elegante de nuestro diseño
    val TextFieldBgColor = Color(0xFFF5F5F5) // El grisecito claro de las cajas de texto

    // Salvavidas para teléfonos pequeños: permite hacer scroll hacia abajo si el teclado tapa cosas
    val scrollState = rememberScrollState()

    // --- NUEVAS HERRAMIENTAS PARA FIREBASE ---
    val context = androidx.compose.ui.platform.LocalContext.current
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

    // CONTENEDOR PRINCIPAL: Ocupa todo y permite scroll
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {

        // --- CAPA 1: HEADER (LA ILUSTRACIÓN) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp) // Altura generosa para que se vea el paisaje
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_login_header),
                contentDescription = "Fondo Login",
                contentScale = ContentScale.FillWidth, // Escala a lo ancho para no dejar huecos
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter) // Pegamos la imagen abajo para no cortar los arbolitos
            )
        }

        // --- CAPA 2: CONTENIDO DEL LOGIN ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp) // Margen a los lados para respirar
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Textos de Bienvenida (El truco de los dos colores)
            Text(
                // buildAnnotatedString nos deja pintar pedacitos de texto de diferente color
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("Vamos a ")
                    }
                    withStyle(style = SpanStyle(color = SyncraDarkBlue)) {
                        append("Iniciar Sesión")
                    }
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ingresa tus credenciales para acceder a tu agenda y propiedades.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- BANNER DE ERROR (DENTRO DEL CONDICIONAL) ---
            // Solo se dibuja en la pantalla si showError es 'true'
            if (showError) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SyncraDarkBlue, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Contraseña o correo incorrecto. Intenta de nuevo.",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- INPUT 1: CORREO ELECTRÓNICO ---
            OutlinedTextField(
                value = email,
                onValueChange = { email = it }, // Actualiza la variable 'email' en tiempo real
                label = { Text("Correo electrónico", color = Color.Gray) },
                // Ponemos el ícono de sobrecito de Material Design a la izquierda
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Icono Correo",
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                // Le quitamos el borde negro feo que trae por defecto y le ponemos nuestro verde al enfocar
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = TextFieldBgColor,
                    focusedContainerColor = TextFieldBgColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = SyncraGreen
                ),
                singleLine = true // Evita que den "Enter" y se haga gigante el campo
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- INPUT 2: CONTRASEÑA ---
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.Gray) },
                // Ponemos el ícono del candado
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Icono Contraseña",
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                // Aquí está la magia: si passwordVisible es false, ponle mascarilla (puntitos). Si es true, quítasela.
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = TextFieldBgColor,
                    focusedContainerColor = TextFieldBgColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = SyncraGreen
                ),
                singleLine = true
            )

            // BOTÓN TEXTO: "Mostrar/Ocultar contraseña"
            TextButton(
                // Al hacer clic, invierte el valor (si era true, se hace false y viceversa)
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.align(Alignment.End) // Alineado a la derecha
            ) {
                Text(
                    text = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    color = SyncraDarkBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- BOTÓN PRINCIPAL: INGRESAR ---
            Button(
                onClick = {
                    // 1. Verificamos que no intenten entrar con los campos vacíos
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        showError = false // Ocultamos el error por si estaba visible

                        // 2. Le pasamos el correo y contraseña a Firebase
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // ¡Entró! Mostramos un mensajito rápido y navegamos
                                    android.widget.Toast.makeText(context, "¡Bienvenido agente! 🕵️‍♂️", android.widget.Toast.LENGTH_SHORT).show()
                                    navController.navigate("home_screen")
                                } else {
                                    // Falló (mala contraseña o correo). Activamos tu banner de error.
                                    showError = true
                                }
                            }
                    } else {
                        // Si dejaron algo vacío, mostramos el banner de error también
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), // Botón bien grueso y fácil de presionar
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SyncraGreen)
            ) {
                Text(
                    text = "Ingresar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SEPARADOR VISUAL " O " ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                Text(
                    text = "  o  ",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- BOTÓN SECUNDARIO: CONTINUAR CON GOOGLE ---
            OutlinedButton(
                onClick = { /* TODO: Configurar Firebase Auth para Google Sign-In */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Logo Google",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Continuar con Google",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- PIE DE PÁGINA: SOPORTE TÉCNICO ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "¿Problemas para ingresar? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Contacta a soporte",
                    color = SyncraDarkBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        try {
                            // Preparamos los textos y los "codificamos" para que Gmail los entienda a la fuerza.
                            val email = "soporte@syncraestate.com"
                            val subject = android.net.Uri.encode("Solicitud de Soporte - Acceso a App")
                            val body = android.net.Uri.encode("Hola equipo de soporte.\n\nSoy un agente de SyncraEstate y estoy teniendo problemas para acceder a mi cuenta desde la aplicación móvil. Por favor, solicito revisión de mis credenciales.\n\nGracias.")

                            // Armamos el súper link con todo incrustado.
                            val uriString = "mailto:$email?subject=$subject&body=$body"

                            val intent = android.content.Intent(
                                android.content.Intent.ACTION_SENDTO,
                                uriString.toUri()
                            )
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Si el celular no tiene app de correos
                            android.widget.Toast.makeText(context, "No tienes una app de correo instalada ✉️", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            // Espacio final de cortesía
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}