package com.example.gameded

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameded.personagem.Personagem

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val personagem = intent.getSerializableExtra("personagem") as? Personagem
        personagem?.aplicarBonus()

        setContent {
            if (personagem != null) {
                MostrarStatusScreen(personagem)
            } else {
                Text("Erro ao carregar personagem")
            }
        }
    }
}

@Composable
fun MostrarStatusScreen(personagem: Personagem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Status Final do Personagem",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Nome e Raça
        StatusText("Nome: ", personagem.nome, fontSize = 18.sp)
        StatusText("Raça: ", personagem.raca?.getNomeFormatado() ?: "Desconhecida", fontSize = 18.sp)

// Atributos com modificadores
        StatusText("Força: ", "${personagem.forca} (Modificador: ${personagem.calcularModificador(personagem.forca)})", fontSize = 18.sp)
        StatusText("Destreza: ", "${personagem.destreza} (Modificador: ${personagem.calcularModificador(personagem.destreza)})", fontSize = 18.sp)
        StatusText("Constituição: ", "${personagem.constituicao} (Modificador: ${personagem.calcularModificador(personagem.constituicao)})", fontSize = 18.sp)
        StatusText("Inteligência: ", "${personagem.inteligencia} (Modificador: ${personagem.calcularModificador(personagem.inteligencia)})", fontSize = 18.sp)
        StatusText("Sabedoria: ", "${personagem.sabedoria} (Modificador: ${personagem.calcularModificador(personagem.sabedoria)})", fontSize = 18.sp)
        StatusText("Carisma: ", "${personagem.carisma} (Modificador: ${personagem.calcularModificador(personagem.carisma)})", fontSize = 18.sp)

// Pontos de Vida
        val vidaTotal = personagem.pontosDeVida + personagem.calcularModificador(personagem.constituicao)
        StatusText("Pontos de Vida: ", vidaTotal.toString(), fontSize = 18.sp)

        Spacer(modifier = Modifier.height(80.dp)) // Adiciona espaço entre o botão e o texto anterior

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Text("Iniciar")
        }

    }
}

@Composable
fun StatusText(label: String, value: String, fontSize: TextUnit = 16.sp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
        Text(
            text = value,
            color = Color.Gray,
            fontSize = fontSize
        )
    }
}

