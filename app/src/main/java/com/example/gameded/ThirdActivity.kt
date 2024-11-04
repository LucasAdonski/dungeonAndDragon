package com.example.gameded

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.gameded.personagem.*

class ThirdActivity : ComponentActivity() {

    private val CHANNEL_ID = "personagem_channel"
    private val NOTIFICATION_ID = 1
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    private val personagemViewModel: PersonagemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val personagemId = intent.getIntExtra("personagem_id", -1)

        if (personagemId != -1) {
            personagemViewModel.getPersonagemById(personagemId).observe(this) { personagemEntity ->
                if (personagemEntity != null) {
                    val personagem = personagemEntity.toPersonagem()
                    setContent {
                        val context = LocalContext.current
                        MostrarStatusScreen(personagem, personagemViewModel, context, personagemEntity.id)
                    }
                } else {
                    setContent {
                        Text("Erro ao carregar personagem")
                    }
                }
            }
        } else {
            setContent {
                Text("Erro ao carregar personagem")
            }
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Personagem Criado"
            val descriptionText = "Notificação para criação de personagem"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification() {

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val intent = Intent(this, ThirdActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Seu personagem foi criado!")
            .setContentText("Clique aqui para continuar.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}

@Composable
fun MostrarStatusScreen(personagem: Personagem, personagemViewModel: PersonagemViewModel, context: Context, personagemId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Status Final do Personagem",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        StatusText("Nome: ", personagem.nome, fontSize = 18.sp)
        StatusText("Raça: ", personagem.raca?.getNomeFormatado() ?: "Desconhecida", fontSize = 18.sp)
        StatusText("Força: ", "${personagem.forca} (Modificador: ${personagem.calcularModificador(personagem.forca)})", fontSize = 18.sp)
        StatusText("Destreza: ", "${personagem.destreza} (Modificador: ${personagem.calcularModificador(personagem.destreza)})", fontSize = 18.sp)
        StatusText("Constituição: ", "${personagem.constituicao} (Modificador: ${personagem.calcularModificador(personagem.constituicao)})", fontSize = 18.sp)
        StatusText("Inteligência: ", "${personagem.inteligencia} (Modificador: ${personagem.calcularModificador(personagem.inteligencia)})", fontSize = 18.sp)
        StatusText("Sabedoria: ", "${personagem.sabedoria} (Modificador: ${personagem.calcularModificador(personagem.sabedoria)})", fontSize = 18.sp)
        StatusText("Carisma: ", "${personagem.carisma} (Modificador: ${personagem.calcularModificador(personagem.carisma)})", fontSize = 18.sp)
        StatusText("Pontos de Vida: ", "${personagem.pontosDeVida}", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            onClick = {
                personagemViewModel.deleteAll()
                Toast.makeText(context, "Todos os personagens foram excluídos.", Toast.LENGTH_SHORT).show()

                val intent = Intent(context, SplashActivity::class.java)
                context.startActivity(intent)
                (context as? ThirdActivity)?.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Text("Excluir Todos os Personagens")
        }

        Button(
            onClick = {
                personagemViewModel.getPersonagemById(personagemId).observeForever { personagemEntity ->
                    if (personagemEntity != null) {
                        personagemViewModel.delete(personagemEntity)
                        Toast.makeText(context, "Personagem excluído.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, SplashActivity::class.java)
                        context.startActivity(intent)
                        (context as? ThirdActivity)?.finish()
                    } else {
                        Toast.makeText(context, "Nenhum personagem encontrado para excluir.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Text("Excluir Personagem")
        }

        Button(
            onClick = {
                personagemViewModel.allPersonagens.observeForever { listaPersonagens ->
                    if (listaPersonagens.isNotEmpty()) {
                        listaPersonagens.forEach { personagemEntity ->
                            println("Personagem: ${personagemEntity.nome}, Raça: ${personagemEntity.nomeRaca}, Força: ${personagemEntity.forca}, Destreza: ${personagemEntity.destreza}, Constituição: ${personagemEntity.constituicao}, Inteligência: ${personagemEntity.inteligencia}, Sabedoria: ${personagemEntity.sabedoria}, Carisma: ${personagemEntity.carisma}, Pontos de Vida: ${personagemEntity.pontosDeVida}")
                        }
                    } else {
                        println("Nenhum personagem encontrado no banco de dados.")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Text("Listar Personagens")
        }

        Button(
            onClick = {
                val intent = Intent(context, SplashActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Text("Voltar ao início")
        }

        Button(
            onClick = {
                val activity = (context as? ThirdActivity) ?: return@Button
                activity.showNotification()
            },
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

