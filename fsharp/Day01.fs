module Day01

open Util


let rec calcBecomeGreater list =
    match list with
    | [] -> 0
    | a :: [] -> 0
    | a :: b :: xs -> 
        let adder = if b > a then 1 else 0
        // printfn "%i;%s" a (if adder = 1 then "WAHR" else "FALSCH")
        adder + calcBecomeGreater (b :: xs)


let rec calcBecomeGreaterSlidingWindow list =
    match list with
    | a :: b :: c :: d :: xs ->
        let sum1 = a+b+c
        let sum2 = b+c+d
        let adder = if sum2 > sum1 then 1 else 0
        // printfn "%i, %i" sum1 sum2
        adder + calcBecomeGreaterSlidingWindow (b :: c :: d :: xs)
    | _ -> 0

let day01 () =
    printfn "Day 1\n============="
    printfn "01: Anzahl: %i" ( calcBecomeGreater ( readLines "input_01" |> List.map int ) )
    printfn "02: Anzahl: %i" ( calcBecomeGreaterSlidingWindow ( readLines "input_01" |> List.map int ) )
