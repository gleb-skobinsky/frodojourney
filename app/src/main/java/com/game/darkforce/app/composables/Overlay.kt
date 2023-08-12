package com.game.darkforce.app.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.darkforce.fonts.Robotronika
import com.game.darkforce.riddles.AnswerVariant
import com.game.darkforce.riddles.riddles
import com.game.darkforce.viewmodel.MainViewModel

@Composable
fun Overlay(overlayOpen: Boolean, viewModel: MainViewModel) {
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    AnimatedVisibility(visible = overlayOpen) {
        RiddlesInterface {
            Box(
                modifier = Modifier
                    .size(250.dp, 500.dp)
                    .align(Alignment.Center)
            ) {
                UiQuestion(currentQuestion) { viewModel.incrementQuestion() }
            }
        }
    }

}

@Composable
private fun BoxScope.UiQuestion(question: Int, next: () -> Unit) {
    when (question) {
        -1 -> {
            TextContent {
                FuturisticText(
                    text = "Неплохо, мой падаван. На вопросы мои ты ответь теперь"
                )
                FuturisticButton("Начать", next)
            }
        }

        in (0 until riddles.size) -> {
            val newQuestion = riddles[question]
            var isSuccess by remember(question) { mutableStateOf(false) }
            if (isSuccess) {
                TextContent {
                    Column {
                        FuturisticText(newQuestion.congratulation)
                        FuturisticButton("Продолжить", next)
                    }
                }
            } else {
                TextContent {
                    FuturisticText(newQuestion.question)
                    Spacer(modifier = Modifier.height(12.dp))
                    for (answer in newQuestion.answers.shuffled()) {
                        PotentialAnswer(answer) { isSuccess = true }
                    }
                }
            }
        }

        else -> {
            TextContent {
                FuturisticText(text = "Поздравляю и я тебя, юный падаван, успешно испытание ты окончил! Желаю тебе, чтобы испытания в жизни все успешно так же проходил ты! С Днём Рождения!")
            }
        }
    }
}

@Composable
private fun FuturisticButton(text: String, next: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.padding(top = 24.dp),
        onClick = {
            next()
        },
    ) {
        FuturisticText(text = text)
    }
}

@Composable
fun PotentialAnswer(answer: AnswerVariant, onSuccess: () -> Unit) {
    var checked by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
            if (answer.correct) {
                onSuccess()
            }
        })
        FuturisticText(text = answer.answer, size = 10.sp)
    }
}

@Composable
fun BoxScope.TextContent(content: @Composable () -> Unit) {
    Column(
        Modifier
            .background(Color.Black.copy(0.5f))
            .align(Alignment.BottomCenter)
    ) {
        content()
    }
}

@Composable
fun FuturisticText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 12.sp,
) = Text(
    text = text,
    color = Color.White,
    modifier = modifier,
    fontFamily = Robotronika.fontFamily,
    fontSize = size
)


