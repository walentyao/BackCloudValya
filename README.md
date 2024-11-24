Для запуска нажать ран в
классе [MarkoServComeBackApplication.java](src/main/java/ru/valya/serveback/MarkoServComeBackApplication.java)
В проекте юзаются Object Storage(S3) и YandexGPT API + капча на фронте https://yandex.cloud/ru/docs/smartcaptcha/?from=int-console-empty-state 
Дока яндекса на реализованный метод https://yandex.cloud/ru/docs/foundation-models/text-generation/api-ref/TextGeneration/completion
Урлы:
1. localhost:8080/roles - Получить все возможные роли, от которых можно писать промты
   Логика под капотом: идем в с3 за файлом gpt.xlsx с помощью AmazonS3Client.getObject() и вычитываем построчно
2. localhost:8080/gpt?text=опиши новинки Apple за 2010 год&userRole=SYSTEM - получить ответ на промт - {text} с ролью - {userRole}
   Логика под капотом:
   1.1 Вызывается метод https://llm.api.cloud.yandex.net/foundationModels/v1/completion
   c хедером Authorization: Api-Key <api-key>
   с телом
   {
   "modelUri": "gpt://b1g704fj4mlmacedi8n8/yandexgpt/rc",
   "completionOptions": {
   "maxTokens": 500,
   "temperature": 0.3
   },
   "messages": [
     {
       "role": "{userRole}",
       "text": "{text}"
     }
   ]
   }
   В ответ получаем ужатый ответ из апишки
   {
   "translatedText": "В 2010 году компания Apple представила несколько новинок, которые стали значимыми событиями в мире технологий.\n\n**1. iPad.** Одним из самых ярких событий года стал выпуск первого планшета от Apple — iPad. Он был представлен 27 января 2010 года и сразу же привлёк внимание своим удобным интерфейсом, мощным процессором и большим объёмом памяти. Планшет предлагал пользователям новый способ взаимодействия с контентом и стал основой для развития целой категории устройств.\n\n**2. iPhone 4.** Ещё одной важной новинкой стал iPhone 4, представленный 7 июня 2010 года. Устройство получило ряд улучшений по сравнению с предыдущими моделями, включая более мощный процессор, новую камеру и обновлённый дизайн. Одной из главных особенностей iPhone 4 стал Retina-дисплей с высоким разрешением, который значительно улучшил качество изображения.\n\n**3. Mac OS X Lion.** Помимо новых устройств, Apple также представила обновление операционной системы — Mac OS X Lion. Она была выпущена 24 октября 2010 года и принесла с собой множество новых функций и улучшений. Среди них были новые приложения, такие как Launchpad для управления приложениями и Mission Control для более удобного переключения между окнами, а также улучшенная интеграция с iCloud.\n\nЭти новинки стали важными шагами в развитии компании Apple и оказали значительное влияние на рынок технологий."
   }