package com.example.gameded
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gameded.personagem.*
import androidx.compose.ui.platform.LocalContext
import androidx.activity.viewModels

class SecondActivity : ComponentActivity() {
    private val personagemViewModel: PersonagemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val personagemId = intent.getIntExtra("personagem_id", -1)

        if (personagemId != -1) {
            personagemViewModel.getPersonagemById(personagemId).observe(this) { personagemEntity ->
                if (personagemEntity != null) {
                    val personagem = personagemEntity.toPersonagem()
                    setContent {
                        DistribuirPontosScreen(personagem, personagemViewModel, personagemEntity.id)
                    }
                } else {
                    Toast.makeText(this, "Erro ao carregar personagem", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            Toast.makeText(this, "ID de personagem inválido", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

@Composable
fun DistribuirPontosScreen(personagem: Personagem, personagemViewModel: PersonagemViewModel, id: Int) {

    val context = LocalContext.current
    var pontosRestantes by remember { mutableStateOf(27) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Distribua os pontos do seu personagem",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, bottom = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Nome: ${personagem.nome}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Raça: ${personagem.raca?.getNomeFormatado() ?: "Sem raça"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

        }

        Text(
            text = "Pontos restantes: $pontosRestantes",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AtributoRow("Força", personagem.forca, pontosRestantes,
                onAdicionar = { custo ->
                    personagem.forca += 1
                    pontosRestantes -= custo
                },
                onRemover = { custo ->
                    personagem.forca -= 1
                    pontosRestantes += custo
                }
            )
            AtributoRow("Destreza", personagem.destreza, pontosRestantes,
                onAdicionar = { custo ->
                    personagem.destreza += 1
                    pontosRestantes -= custo
                },
                onRemover = { custo ->
                    personagem.destreza -= 1
                    pontosRestantes += custo
                }
            )
            AtributoRow("Constituição", personagem.constituicao, pontosRestantes,
                onAdicionar = { custo ->
                    personagem.constituicao += 1
                    pontosRestantes -= custo
                },
                onRemover = { custo ->
                    personagem.constituicao -= 1
                    pontosRestantes += custo
                }
            )
            AtributoRow("Inteligência", personagem.inteligencia, pontosRestantes,
                onAdicionar = { custo ->
                    personagem.inteligencia += 1
                    pontosRestantes -= custo
                },
                onRemover = { custo ->
                    personagem.inteligencia -= 1
                    pontosRestantes += custo
                }
            )
            AtributoRow("Sabedoria", personagem.sabedoria, pontosRestantes,
                onAdicionar = { custo ->
                    personagem.sabedoria += 1
                    pontosRestantes -= custo
                },
                onRemover = { custo ->
                    personagem.sabedoria -= 1
                    pontosRestantes += custo
                }
            )
            AtributoRow("Carisma", personagem.carisma, pontosRestantes,
                onAdicionar = { custo ->
                    personagem.carisma += 1
                    pontosRestantes -= custo
                },
                onRemover = { custo ->
                    personagem.carisma -= 1
                    pontosRestantes += custo
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    personagem.raca?.aplicarBonus(personagem)
                    personagem.pontosDeVida += personagem.calcularModificador(personagem.constituicao)
                    val personagemEntity = personagem.toEntity(id)
                    personagemViewModel.update(personagemEntity)

                    val intent = Intent(context, ThirdActivity::class.java).apply {
                        putExtra("personagem_id", personagemEntity.id)
                    }
                    context.startActivity(intent)
                },
                enabled = pontosRestantes == 0,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Finalizar")
            }

        }
    }
}


@Composable
fun AtributoRow(
    atributo: String,
    valorAtual: Int,
    pontosRestantes: Int,
    onAdicionar: (Int) -> Unit,
    onRemover: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(atributo)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = valorAtual.toString(),
                modifier = Modifier.width(40.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    val custo = if (valorAtual > 13) 2 else 1
                    if (valorAtual > 8) {
                        onRemover(custo)
                    }
                },
                enabled = valorAtual > 8,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Remover")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    val custo = calcularCusto(valorAtual, 1)
                    if (valorAtual < 15 && custo <= pontosRestantes) {
                        onAdicionar(custo)
                    }
                },
                enabled = (valorAtual < 15 && calcularCusto(valorAtual, 1) <= pontosRestantes),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Adicionar")
            }
        }
    }
}

fun calcularCusto(valorAtual: Int, pontosAdicionar: Int): Int {
    var custo = 0
    var valor = valorAtual

    for (i in 1..pontosAdicionar) {
        custo += if (valor >= 13) 2 else 1
        valor++
    }

    return custo
}