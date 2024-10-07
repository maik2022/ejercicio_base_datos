package com.example.explicacion_base_datos.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.explicacion_base_datos.Model.User
import com.example.explicacion_base_datos.Repository.UserRepository
import com.example.explicacion_base_datos.ui.theme.viewUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun UserApp(userRepository: UserRepository, viewModel:viewUsers){

    var nombre by remember { mutableStateOf("")}
    var apellido by remember { mutableStateOf("")}
    var edad by remember { mutableStateOf("")}
    var idUsuario by remember { mutableStateOf("")}
    var idBuscar by remember { mutableStateOf("")}
    var scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
    }

    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .padding(bottom = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Text(
                text = "FORMULARIO DE REGISTRO",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold

            )

            TextField(
                value = nombre,
                onValueChange = {nombre = it},
                label = {Text("Nombre")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = apellido,
                onValueChange = {apellido = it},
                label = {Text("Apellido")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = edad,
                onValueChange = {edad = it},
                label = {Text("Edad")},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {

                    if (nombre.isBlank() || apellido.isBlank() || edad.isBlank() || edad.toIntOrNull() == null){

                        Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()

                    }else{
                        val user = User(
                            nombre = nombre,
                            apellido = apellido,
                            edad = edad.toIntOrNull() ?: 0
                        )
                        //Corrutina para insertar el registro

                        scope.launch {
                            withContext(Dispatchers.IO){
                                userRepository.insert(user)
                            }
                            Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_SHORT).show()

                            viewModel.obtenerUsuario()

                            nombre = ""
                            apellido = ""
                            edad = ""
                        }
                    }

                },

                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Registrar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "LISTA DE USUARIOS",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold

            )

            var users by remember { mutableStateOf(listOf<User>()) }

            Button(
                onClick = {
                    viewModel.obtenerUsuario()
                },

                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Listar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            viewModel.listUsers.forEach {user ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "ID: ${user.id}\nNombre: ${user.nombre}\nApellido: ${user.apellido}\nEdad: ${user.edad}",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }

            }

            Text(
                text = "BORRAR USUARIO",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = idUsuario,
                onValueChange = {idUsuario = it},
                label = {Text("ID")},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (idUsuario.isBlank()){
                        Toast.makeText(context, "Debe colocar un valor", Toast.LENGTH_SHORT).show()
                    }else{

                        scope.launch{

                            val usuarioBorrado = withContext(Dispatchers.IO){
                                userRepository.buscarId(idUsuario.toInt())
                            }

                            if(usuarioBorrado != null){
                                withContext(Dispatchers.IO){
                                    userRepository.deleteById(idUsuario.toInt())
                                }

                                Toast.makeText(context, "Usuario Borrado", Toast.LENGTH_SHORT).show()
                                viewModel.obtenerUsuario()
                                idUsuario = ""
                            }else{
                                Toast.makeText(context, "El usuario no existe", Toast.LENGTH_SHORT).show()
                                idUsuario = ""
                            }

                        }
                    }

                },

                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Borrar")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "ACTUALIZAR USUARIO",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = idBuscar,
                onValueChange = {idBuscar = it},
                label = {Text("ID BUSCADO")},
                modifier = Modifier.fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(8.dp))

            var nombreActualizado by remember { mutableStateOf("")}
            var apellidoActualizado by remember { mutableStateOf("")}
            var edadActualizado by remember { mutableStateOf("")}
            var mostrarFormulario by remember { mutableStateOf(false)}

            Button(
                onClick = {
                    if (idBuscar.isBlank()){

                        Toast.makeText(context, "Debe colocar un valor", Toast.LENGTH_SHORT).show()

                    }else{
                        scope.launch{
                            val usuario = withContext(Dispatchers.IO){
                                userRepository.buscarId(idBuscar.toInt())
                            }

                            if (usuario != null){

                                nombreActualizado = usuario.nombre
                                apellidoActualizado = usuario.apellido
                                edadActualizado = usuario.edad.toString()
                                mostrarFormulario = true

                            }else{

                                Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                                mostrarFormulario = false

                            }

                        }
                    }

                },

                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Buscar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if(mostrarFormulario){

                TextField(
                    value = nombreActualizado,
                    onValueChange = {nombreActualizado = it},
                    label = {Text("Nombre")},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = apellidoActualizado,
                    onValueChange = {apellidoActualizado = it},
                    label = {Text("Apellido")},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = edadActualizado,
                    onValueChange = {edadActualizado = it},
                    label = {Text("edad")},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        scope.launch{
                            withContext(Dispatchers.IO){
                                userRepository.updateUser(idBuscar.toInt(), nombreActualizado, apellidoActualizado, edadActualizado.toInt())
                            }

                            mostrarFormulario = false
                            viewModel.obtenerUsuario()
                            idBuscar = ""
                        }
                    },

                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(text = "Actualizar")
                }

            }


        }


    }
}