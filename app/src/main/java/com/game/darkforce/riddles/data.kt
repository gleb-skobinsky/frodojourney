package com.game.darkforce.riddles

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf

@Stable
data class Riddle(
    val question: String,
    val answers: List<AnswerVariant>,
    val answered: Boolean = false,
    val congratulation: String
)

@Stable
data class AnswerVariant(
    val answer: String,
    val correct: Boolean = false
)

val riddles = mutableStateListOf(
    Riddle(
        question = "Спрашивает Анна тебя: какую вы вместе книгу читать первой начали?",
        answers = listOf(
            AnswerVariant("\"Убыр\", Н. Измайлов"),
            AnswerVariant("\"Фауст\", И. Гёте", true),
            AnswerVariant("\"Эшелон на Самарканд\", Г. Яхина"),
            AnswerVariant("Сборник стихотворений С. Есенина")
        ),
        congratulation = "Верный ответ! Желает тебе Анна стремлений высоких, к познанию большой тяги и интереса, а также смелости великой для всех своих целей и желаний достижения и исполнения."
    ),
    Riddle(
        question = "Данил спрашивает: что делать нужно, если пьян ты, и киоск с шавермой перед тобой стоит?",
        answers = listOf(
            AnswerVariant("Шаверму в нем купить"),
            AnswerVariant("Продавцу выпить предложить"),
            AnswerVariant("На стенке член нарисовать"),
            AnswerVariant("На киоск тот залезть", true)
        ),
        congratulation = "Надежда есть, что повторишь ты это, если на то необходимость будет, очередной киоск покоришь. Человек хороший, мужчина настоящий, душа добрая, в мире современном качества эти сохранить тяжело. Не потерять их тебе желаю. Но и свои интересы и желания не забывай отстаивать и реализовывать."
    ),
    Riddle(
        question = "Спрашивает Мария тебя: встретились мы где в первый раз?",
        answers = listOf(AnswerVariant("МГУ"), AnswerVariant("Бар"), AnswerVariant("Кальянная", true), AnswerVariant("Театр")),
        congratulation = "С Рождения днем поздравляю тебя. Силу приумножай свою, и в трудностях не печалься. Жизнь твоя в руках твоих, пусть будет хорошо все у тебя. Друзей и любовь свою не позабудь, и береги, что есть. А что будет, от тебя зависит."
    ),
    Riddle(
        question = "Илона спрашивает тебя: какое научил ты готовить ее блюдо?",
        answers = listOf(AnswerVariant("Блины", true), AnswerVariant("Стейк"), AnswerVariant("Аджарули"), AnswerVariant("Цзяоцзы")),
        congratulation = "Желает Илона тебе всего лучшего самого-самого, счастья здоровья крепкого близким твоим и тебе."
    ),
    Riddle(
        question = "Спрашивает Кристина тебя: что при первой встрече нашей разлилось у тебя?",
        answers = listOf(AnswerVariant("Сок", true), AnswerVariant("Молоко"), AnswerVariant("Пиво"), AnswerVariant("Вино")),
        congratulation = "Желает Кристина тебе в любых успехов начинаниях и хочет, сбывались чтобы все мечты твои заветные самые."
    ),
    Riddle(
        question = "На вопрос Ульяны каверзный ответить придется тебе: название какое у спектакля, в одном составе были вы в котором, но на сцене не случилось его играть?",
        answers = listOf(AnswerVariant("\"Барышня-крестьянка\"", true), AnswerVariant("\"Сказ про Федота-стрельца, удалого молодца\""), AnswerVariant("\"Палата бизнес-класса\""), AnswerVariant("\"Примадонны\"")),
        congratulation = "Не подвела память твоя! Надеется Ульяна, что удача спутником для тебя верным станет. Что люди близкие будут с тобой всегда. Что в буднях твоих будет и для важного, и для любимого места достаточно. С рождения твоего днем!"
    ),
    Riddle(
        question = "Спрашивает Алексей тебя: как назывался праздник в 1609, который мы регулярно отмечали?",
        answers = listOf(AnswerVariant("День Нептуна", true), AnswerVariant("Иван Купала"), AnswerVariant("День печенья"), AnswerVariant("Всемирный день жареного мяса")),
        congratulation = "Ответ верный! Желаю тебе, чтобы ты был таким же благородным, как Ахиллес, сильным, как Геракл, неотразим, как Аполлон, обладал мудростью Афины. По жизни пусть тебя благословляет Ника, а твои поступки чтобы одобряла Дика."
    ),
    Riddle(
        question = "Вопрошает тебя Валерий: «артефакт» какой чудодейственный и габаритный однажды в обитель транспортную доставляли мы, боясь обнаруженными быть тёмной стороной из-за масок отсутствия?",
        answers = listOf(AnswerVariant("Ковер", true), AnswerVariant("Рулон обоев"), AnswerVariant("Баллон с водой"), AnswerVariant("Сумки с вещами")),
        congratulation = "Истина открылась тебе, друг юный. Желает тебе Валерий радости жизненной, успехов вероятно-невероятных, силы чувство и тяги мощнейшей к исполнению заветных самых желаний своих!"
    )
)