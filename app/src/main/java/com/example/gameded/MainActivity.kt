package com.example.gameded

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gameded.personagem.*
import android.content.Intent
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.activity.viewModels


class MainActivity : ComponentActivity() {
    private val personagemViewModel: PersonagemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(personagemViewModel)
        }
    }
}

@Composable
fun MyApp(personagemViewModel: PersonagemViewModel) {
    CriarPersonagemScreen(
        personagemViewModel = personagemViewModel,
        onCharacterCreated = { personagem ->
        println("Personagem criado: Nome=${personagem.nome}, Raça=${personagem.raca}")
    })
}

@Composable
fun CriarPersonagemScreen(
    personagemViewModel: PersonagemViewModel,
    modifier: Modifier = Modifier,
    onCharacterCreated: (Personagem) -> Unit
) {
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var racaSelecionada by remember { mutableStateOf("Selecione a raça") }

    val racas = listOf(
        "Elfo", "Anão", "Alto Elfo", "Anão da colina","Anão da montanha", "Draconato",
        "Drow", "Elfo da floresta", "Gnomo", "Gnomo da floresta",
        "Gnomo das rochas", "Halfling", "Halfling pés-leves",
        "Halfling robusto", "Humano", "Meio Elfo", "Meio Orc", "Tiefling"
    )

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Selecione o nome do seu personagem:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize * 1.2f),
            modifier = Modifier
                .padding(top = 30.dp, bottom = 20.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Personagem") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecione a raça:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize * 1.2f),
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.weight(1f)) {

                for (raca in racas.take(racas.size / 2)) {
                    Button(
                        onClick = {
                            racaSelecionada = raca
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (racaSelecionada == raca) MaterialTheme.colorScheme.primary else Color.LightGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = raca,
                            color = if (racaSelecionada == raca) Color.White else Color.Black
                        )
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                for (raca in racas.drop(racas.size / 2)) {
                    Button(
                        onClick = {
                            racaSelecionada = raca
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (racaSelecionada == raca) MaterialTheme.colorScheme.primary else Color.LightGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = raca,
                            color = if (racaSelecionada == raca) Color.White else Color.Black
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                val personagem = Personagem().apply {
                    this.nome = nome
                    this.raca = getRacaFromString(racaSelecionada)
                }
                if (personagem.nome.isNotEmpty() && personagem.raca != null) {
                    val personagemEntity = personagem.toEntity()
                    personagemViewModel.insert(personagemEntity) { idInserido ->
                        val intent = Intent(context, SecondActivity::class.java).apply {
                            putExtra("personagem_id", idInserido.toInt())
                        }
                        context.startActivity(intent)
                    }
                    onCharacterCreated(personagem)
                } else {
                    Toast.makeText(context, "Por favor, insira um nome e selecione uma raça.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Avançar")
        }
    }
}



