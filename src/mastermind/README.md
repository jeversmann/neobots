mastermind
==========

A small library built around the Master Mind board game.
Compile with `$javac *.java` and run with default values by going up a directory and calling `$java masterMind/Mastermind`

Advanced usage: `$java masterMind/Mastermind [ mode ] [ set ] [ length ]`

  mode

    -a  Adversary mode; AI makes guessing as difficult as possible

    -e  Evaluator mode; AI attempts to guess a combinaiton of your choice

    -s  Self-Play mode; the Evaluator solves a random combination

    -q  Quiet-Play mode; Evaluator repeatedly solves random combinations, outputting only after each guess

    -b  Battle mode; Points the Adversary and Evaluator AIs at each other, outputting after each game

  set

    int[n]   Uses a set of numbers 0-[n] (up to 9) as valid guesses

    char[n]  Uses a set of [n] characters starting at a for guesses

  length

    [n] Uses combinations of length [n]
