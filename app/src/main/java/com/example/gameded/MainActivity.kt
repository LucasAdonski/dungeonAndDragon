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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    CriarPersonagemScreen(onCharacterCreated = { personagem ->
        println("Personagem criado: Nome=${personagem.nome}, Raça=${personagem.raca}")
    })
}

@Composable
fun CriarPersonagemScreen(
    modifier: Modifier = Modifier,
    onCharacterCreated: (Personagem) -> Unit
) {
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var racaSelecionada by remember { mutableStateOf("Selecione a raça") }

    // Lista das raças disponíveis
    val racas = listOf(
        "Elfo", "Anão", "Alto Elfo", "Anão da colina", "Draconato",
        "Drow", "Elfo da floresta", "Gnomo", "Gnomo da floresta",
        "Gnomo das rochas", "Halfling", "Halfling pés-leves",
        "Halfling robusto", "Humano", "Meio Elfo", "Meio Orc", "Tiefling"
    )

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        // Texto explicativo antes do campo de entrada
        Text(
            text = "Selecione o nome do seu personagem:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize * 1.2f), // Deixa o texto em negrito
            modifier = Modifier
                .padding(top = 30.dp, bottom = 20.dp) // Adiciona espaçamento acima e abaixo do texto
                .fillMaxWidth() // Faz o texto ocupar a largura total
                .wrapContentWidth(Alignment.CenterHorizontally) // Centraliza horizontalmente
        )

        // Campo de texto para o nome do personagem
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Personagem") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Título para a seleção de raça
        Text(
            text = "Selecione a raça:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize * 1.2f), // Deixa o texto em negrito
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp) // Adiciona espaçamento acima e abaixo do texto
                .fillMaxWidth() // Faz o texto ocupar a largura total
                .wrapContentWidth(Alignment.CenterHorizontally) // Centraliza horizontalmente
        )

        // Divisão de 2 para 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribui as colunas igualmente
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Primeira metade dos botões
                for (raca in racas.take(racas.size / 2)) {
                    Button(
                        onClick = {
                            racaSelecionada = raca
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (racaSelecionada == raca) MaterialTheme.colorScheme.primary else Color.LightGray // Cor de fundo
                        ),
                        modifier = Modifier
                            .fillMaxWidth() // Faz com que o botão ocupe a largura total
                            .padding(vertical = 4.dp) // Espaçamento vertical entre os botões
                    ) {
                        Text(
                            text = raca,
                            color = if (racaSelecionada == raca) Color.White else Color.Black // Cor do texto
                        )
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                // Segunda metade dos botões
                for (raca in racas.drop(racas.size / 2)) {
                    Button(
                        onClick = {
                            racaSelecionada = raca
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (racaSelecionada == raca) MaterialTheme.colorScheme.primary else Color.LightGray // Cor de fundo
                        ),
                        modifier = Modifier
                            .fillMaxWidth() // Faz com que o botão ocupe a largura total
                            .padding(vertical = 4.dp) // Espaçamento vertical entre os botões
                    ) {
                        Text(
                            text = raca,
                            color = if (racaSelecionada == raca) Color.White else Color.Black // Cor do texto
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
                    val intent = Intent(context, SecondActivity::class.java).apply {
                        putExtra("personagem", personagem)
                    }
                    context.startActivity(intent)
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

fun getRacaFromString(raca: String): Raca? {
    return when (raca.lowercase().trim()) {
        "elfo" -> Elfo()
        "anão" -> Anao()
        "alto elfo" -> altoElfo()
        "anão da colina" -> anaoColina()
        "draconato" -> Draconato()
        "drow" -> Drow()
        "elfo da floresta" -> elfoFloresta()
        "gnomo" -> Gnomo()
        "gnomo da floresta" -> gnomoFloresta()
        "gnomo das rochas" -> gnomoRochas()
        "halfling" -> Halfling()
        "halfling pés-leves" -> halflingPesLeves()
        "halfling robusto" -> halflingRobusto()
        "humano" -> Humano()
        "meio elfo" -> meioElfo()
        "meio orc" -> meioOrc()
        "tiefling" -> Tiefling()
        else -> null
    }
}

fun Raca.getNomeFormatado(): String {
    return when (this) {
        is Elfo -> "Elfo"
        is Anao -> "Anão"
        is altoElfo -> "Alto Elfo"
        is anaoColina -> "Anão da Colina"
        is Draconato -> "Draconato"
        is Drow -> "Drow"
        is elfoFloresta -> "Elfo da Floresta"
        is Gnomo -> "Gnomo"
        is gnomoFloresta -> "Gnomo da Floresta"
        is gnomoRochas -> "Gnomo das Rochas"
        is Halfling -> "Halfling"
        is halflingPesLeves -> "Halfling Pés-Leves"
        is halflingRobusto -> "Halfling Robusto"
        is Humano -> "Humano"
        is meioElfo -> "Meio Elfo"
        is meioOrc -> "Meio Orc"
        is Tiefling -> "Tiefling"
        else -> "Raça desconhecida"
    }
}


