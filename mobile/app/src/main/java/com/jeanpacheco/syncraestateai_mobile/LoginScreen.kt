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

// Librerías para el funcionamiento de Continuar con Google.
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavController) {
    // --- 1. MEMORIA DE LA PANTALLA (ESTADOS) ---
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Interruptor para ver la contraseña (true = se ve, false = puntitos)
    var passwordVisible by remember { mutableStateOf(false) }

    // Interruptor para mostrar el banner de error simulado
    var showError by remember { mutableStateOf(false) }

    // --- 2. PALETA DE COLORES ---
    val SyncraDarkBlue = Color(0xFF1F4C6B)
    val TextFieldBgColor = Color(0xFFF5F5F5)

    // Salvavidas para teléfonos pequeños: permite hacer scroll hacia abajo si el teclado tapa cosas
    val scrollState = rememberScrollState()

    // --- HERRAMIENTAS PARA FIREBASE ---
    val context = androidx.compose.ui.platform.LocalContext.current
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

    // --- LANZADOR DE GOOGLE SIGN-IN ---
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                // Obtenemos el token de Google y se lo pasamos a Firebase
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            android.widget.Toast.makeText(context, "¡Bienvenido con Google!", android.widget.Toast.LENGTH_SHORT).show()
                            navController.navigate("home_screen")
                        } else {
                            showError = true
                        }
                    }
            } catch (e: ApiException) {
                // Si el usuario cancela o hay error
                showError = true
            }
        }
    }

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
                .height(280.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_login_header),
                contentDescription = "Fondo Login",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
            )
        }

        // --- CAPA 2: CONTENIDO DEL LOGIN ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Textos de Bienvenida
            Text(
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

            // --- BANNER DE ERROR ---
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
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Icono Correo",
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = TextFieldBgColor,
                    focusedContainerColor = TextFieldBgColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = SyncraGreen
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- INPUT 2: CONTRASEÑA ---
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Icono Contraseña",
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = TextFieldBgColor,
                    focusedContainerColor = TextFieldBgColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = SyncraGreen
                ),
                singleLine = true
            )

            // BOTÓN TEXTO: "Mostrar/Ocultar contraseña"
            TextButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.align(Alignment.End)
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
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        showError = false
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    android.widget.Toast.makeText(context, "¡Bienvenido agente!", android.widget.Toast.LENGTH_SHORT).show()
                                    navController.navigate("home_screen")
                                } else {
                                    showError = true
                                }
                            }
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                onClick = {
                    showError = false
                    // 1. Configuramos qué le vamos a pedir a Google (el correo y el ID del usuario)
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("228214220825-5djom5v4gjkqsv0ufib6nddjegbao8dv.apps.googleusercontent.com")
                        .requestEmail()
                        .build()

                    // 2. Preparamos el cliente
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)

                    // 3. ¡Lanzamos la ventanita de Google!
                    googleSignInLauncher.launch(googleSignInClient.signInIntent)
                },
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
                            val email = "soporte@syncraestate.com"
                            val subject = android.net.Uri.encode("Solicitud de Soporte - Acceso a App")
                            val body = android.net.Uri.encode("Hola equipo de soporte.\n\nSoy un agente de SyncraEstate y estoy teniendo problemas para acceder a mi cuenta desde la aplicación móvil. Por favor, solicito revisión de mis credenciales.\n\nGracias.")

                            val uriString = "mailto:$email?subject=$subject&body=$body"

                            val intent = android.content.Intent(
                                android.content.Intent.ACTION_SENDTO,
                                uriString.toUri()
                            )
                            context.startActivity(intent)
                        } catch (e: Exception) {
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