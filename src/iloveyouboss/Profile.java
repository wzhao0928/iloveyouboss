/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
 ***/
package iloveyouboss;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Profile {

    private Map<String, Answer> answers = new HashMap<>();
    // ...

    private int score;
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;

        boolean kill = false;
        boolean anyMatches = false;
        for (Criterion criterion : criteria) {  // criteria: null, 0, 1, many
            Answer answer = answers.get(
                    criterion.getAnswer().getQuestionText());   // criterion.getAnswer(): null, answer.get: exception
            boolean match =
                    (criterion.getWeight() == Weight.DontCare) ||   // (criterion.getWeight() == Weight.DontCare) true,
                            answer.match(criterion.getAnswer());    //  criterion.getWeight() == Weight.DontCare false && answer.match true or false
            if (!match && criterion.getWeight() == Weight.MustMatch) { // (criterion.getWeight() == Weight.MustMatch) true or false
                kill = true;
            }
            if (match) {
                score += criterion.getWeight().getValue();  // score's value
            }
            anyMatches |= match;
            // ...
        }
        if (kill)
            return false;
        return anyMatches;
//        (criteria = null) : exception
//        (criterion.getAnswer() return null) : exception
//        (criterion.getAnswer().getQuestionText() return null) : exception
//        (answer.get return null) : exception

//        (criteria has 0 elem) : return false, score == 0
//        (criteria has 1 elem, criterion.getWeight() == Weight.DontCare) : score == criterion's value, return true
//        (criteria has 1 elem, criterion.getWeight() != Weight.DontCare, answer.match() == true) : score == criterion's value, return true
//        (criteria has 1 elem, criterion.getWeight() != Weight.DontCare, answer.match() == false, criterion.getWeight() == Weight.MustMatch) : score == 0, return false
//        (criteria has n elem, criterions none match) : return false, score not change
//        (criteria has n elem, criterions all match) : return true, score == sum(criterions' value)
//        (criteria has n elem, m criterions match, no kill) : return true, score == sum(m criterions' value)
//        (criteria has n elem, m criterions match, killed) : return false, score == sum(m criterions' value)
    }

    public int score() {
        return score;
    }

    public List<Answer> classicFind(Predicate<Answer> pred) {
        List<Answer> results = new ArrayList<Answer>();
        for (Answer answer : answers.values())
            if (pred.test(answer))
                results.add(answer);
        return results;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Answer> find(Predicate<Answer> pred) {
        return answers.values().stream()
                .filter(pred)
                .collect(Collectors.toList());
    }
}
