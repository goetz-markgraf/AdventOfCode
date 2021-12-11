# AdventOfCode 2021

Solutions of the challenges of https://adventofcode.com/2021 in kotlin

Only from day 05 because the first 4 days I worked in F#. 

## how to build

This is a simple kotlin project without any maven / gradle etc. Clone and create a new kotlin
project for it.

the only non kotlin-standard library I've added is `assertJ` in the corresponding directory.
Add it to your path.

## how to run

All days reside in single files within the root package (yes, I know, this is bad taste). Each day
has a "main"-function for it that executes the day and prints the result.
Oftentimes you only see the "part 2"-result because I have reworked the code.

The main.kt-File has a main-function that calls other functions.

The input-files for test and the real puzzle input resides in files in the `resources`-folder 

From Day09 on, I have written tests prior to solving the puzzle. The test only utilizes the
test-input while the "main"-function of the day only uses the real puzzle input.

# have fun

Goetz
