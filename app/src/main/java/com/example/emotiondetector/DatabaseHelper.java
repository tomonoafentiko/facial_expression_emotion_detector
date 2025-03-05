package com.example.emotiondetector;

import java.util.Random;

public class DatabaseHelper {

    // Arrays of sad and happy insights (quotes, bible verses, paragraphs)
    private static final String[][] SAD_INSIGHTS = {
            {
                    "Every storm runs out of rain. – Maya Angelou",
                    "The Lord is close to the brokenhearted and saves those who are crushed in spirit. – Psalm 34:18",
                    "It's okay to feel sad. Emotions are a natural response to life's ups and downs. Allow yourself to experience this feeling, knowing that it's part of your journey and will eventually pass.",
                    "This too shall pass. – Persian adage",
                    "He will wipe every tear from their eyes. There will be no more death or mourning or crying or pain. – Revelation 21:4",
                    "When sadness feels overwhelming, remember that it's just a moment in time. Reach out to someone who cares, and let them help you carry the weight of your emotions.",
                    "There is no despair so absolute as that which comes with the first moments of our first great sorrow, when we have not yet known what it is to have suffered and be healed, to have despaired and recovered hope. – George Eliot",
                    "Cast all your anxiety on him because he cares for you. – 1 Peter 5:7",
                    "In the darkest moments, hope may seem distant. But every night gives way to dawn. Hold on, for light always follows darkness.",
                    "The way to get through anything mentally painful is to keep asking yourself, 'What’s next?' It's in the answer that you'll find hope and motivation. – Les Brown",
                    "When you pass through the waters, I will be with you; and when you pass through the rivers, they will not sweep over you. – Isaiah 43:2",
                    "Sadness, like all emotions, is temporary. It is a reminder of the times you have loved deeply and cared intensely. Allow it to teach you and make you stronger.",
                    "Out of difficulties grow miracles. – Jean de La Bruyère",
                    "Blessed are those who mourn, for they will be comforted. – Matthew 5:4",
                    "Take a moment to breathe deeply and center yourself. Your sadness is a valid emotion, but it doesn't define you. You are resilient and will emerge from this stronger.",
                    "Sometimes, carrying on, just carrying on, is the superhuman achievement. – Albert Camus",
                    "Weeping may stay for the night, but rejoicing comes in the morning. – Psalm 30:5",
                    "paragraph: It’s okay to seek comfort in the little things—a warm cup of tea, a good book, or the presence of a loved one. These small comforts remind us of life's simple joys even in sorrowful times.",
                    "Our greatest glory is not in never falling, but in rising every time we fall. – Confucius",
                    "Even though I walk through the darkest valley, I will fear no evil, for you are with me. – Psalm 23:4",
                    "Remember, resilience comes from facing challenges. Every setback strengthens you, and every sorrow holds a hidden lesson."}};

    private static final String[][] HAPPY_INSIGHTS = {
            {"The joy of the Lord is your strength. – Nehemiah 8:10",
                    "Happiness depends upon ourselves. – Aristotle",
                    "Joy is not in things; it is in us. – Richard Wagner",
                    "Rejoice in the Lord always. I will say it again: Rejoice! – Philippians 4:4",
                    "True happiness comes from within and flows outward. – Unknown",
                    "The Lord has done great things for us, and we are filled with joy. – Psalm 126:3",
                    "Happiness is not by chance, but by choice. – Jim Rohn",
                    "Delight yourself in the Lord, and He will give you the desires of your heart. – Psalm 37:4",
                    "Joy is the simplest form of gratitude. – Karl Barth",
                    "When you have a joyful heart, you have the power to transform your circumstances. – Unknown",
                    "Blessed are the people who know the joyful sound! – Psalm 89:15",
                    "Happiness is a warm puppy. – Charles M. Schulz",
                    "May your hearts always be filled with joy, and may your spirit soar high in all things you do. – Unknown",
                    "For you make me glad by your deeds, O Lord; I sing for joy at the works of your hands. – Psalm 92:4",
                    "Joy is the most infallible sign of the presence of God. – Pierre Teilhard de Chardin",
                    "The happiness of your life depends upon the quality of your thoughts. – Marcus Aurelius",
                    "For with you is the fountain of life; in your light we see light. – Psalm 36:9",
                    "Life is short, and there's no time to leave important words unsaid. – Unknown",
                    "The Lord has made everything beautiful in its time. – Ecclesiastes 3:11",
                    "Happiness is not a goal; it’s a by-product of a life well-lived. – Eleanor Roosevelt"}};

    private final Random random = new Random();

    // Method to get a random sad insight
    public String getSadInsight() {
        int index = random.nextInt(SAD_INSIGHTS.length);
        String[] insight = SAD_INSIGHTS[index];
        int insightType = random.nextInt(3); // 0 for quote, 1 for bible verse, 2 for paragraph
        return insight[insightType];
    }

    // Method to get a random happy insight
    public String getHappyInsight() {
        int index = random.nextInt(HAPPY_INSIGHTS.length);
        String[] insight = HAPPY_INSIGHTS[index];
        int insightType = random.nextInt(3); // 0 for quote, 1 for bible verse, 2 for paragraph
        return insight[insightType];
    }

    // New method to suggest professional help if the emotion is sad
    public String suggestProfessionalHelp() {
        return "Would you like to consult professional help? We can assist you in finding the right support.";
    }
}
