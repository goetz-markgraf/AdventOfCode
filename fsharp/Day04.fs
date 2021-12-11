module Day04

open System
open Util

let SIZE = 5

type Number = {
    Number: int
    Marked: bool
}

type GameField = Number[,]

let createField (list: string[]) =
    let ret = Array2D.create 5 5 { Number = -1; Marked = false}

    let putIntoRet pos (line: string) =
        let numbers =
            line.Split([| ' '|], StringSplitOptions.RemoveEmptyEntries)
            |> Seq.toArray
            |> Array.map int

        for i in 0..4 do
            ret[pos, i] <- { Number = numbers[i]; Marked = false }


    for i in 0 .. 4 do  
        list[i] |> putIntoRet i

    ret



let rec createFields lines =
    match lines with
    | l1 :: l2 :: l3 :: l4 :: l5 :: l6 :: xs -> 
        createField [| l2; l3; l4; l5; l6 |] :: createFields xs
    | _ -> []



let mark (field: Number[,]) number =
    for i in 0..4 do
        for j in 0..4 do
            let num = field[i,j]
            if num.Number = number then field[i,j] <- { num with Marked = true }



let markAll (fields: Number[,] list) number =
    fields
    |> List.iter (fun eachField ->
        mark eachField number)



let isFull (row:Number[]) =
    row[0].Marked && row[1].Marked && row[2].Marked && row[3].Marked && row[4].Marked



let checkWinning (field: Number[,]) =
    let mutable winningRow = -1
    let mutable winningCol = -1
    for i in 0..4 do if isFull field[i,*] then winningRow <- i
    for i in 0..4 do if isFull field[*,i] then winningCol <- i

    winningCol >= 0 || winningRow >= 0



// let checkWinnings fields =
//     fields |> List.tryFind (fun f -> checkWinning f |> Option.isSome)


let diffArray (a1: Number[,]) (a2: Number[,]) =
    let mutable dif = false
    for i in 0..4 do
        for j in 0..4 do
            if (a1[i,j].Number <> a2[i,j].Number || a1[i,j].Marked <> a2[i,j].Marked ) then dif <- true

    dif


let printField (field: Number[,]) =
    for i in 0..4 do    
        for j in 0..4 do
            printf "(%i, %b) " field[i,j].Number field[i,j].Marked
        printfn ""


let printFields fields =
    fields |> List.iter (fun f ->
        printField f
        printfn "")




let listRemove (item: Number[,]) (list: Number[,] list) =
    printfn "**************************** Remove this field:"
    printField item

    let ret = List.filter (fun i -> diffArray item i) list
    
    // printfn "********* new fields ****************"
    // printFields ret
    ret


let rec processUntilWinning drawnNumbers fields winningField =
    match drawnNumbers with
    | [] -> winningField
    | x :: xs ->
        // printfn "Take number: %i" x
        // printFields fields

        markAll fields x
        let (winners, restFields) = fields |> List.partition checkWinning
        match winners with
        | [] -> processUntilWinning xs fields winningField
        | _ ->
            processUntilWinning xs restFields (Some (x, winners |> List.last))



let calculateScore x (field: Number[,]) =
    let mutable sum = 0
    for i in 0..4 do
        for j in 0..4 do
            if not field[j,i].Marked then sum <- sum + field[j,i].Number

    sum


let day04() =
    printfn "Day04:\n================="

    let input = readLines "input_04"

    match input with
    | first :: rest ->
        
        let drawnNumbers =  first.Split [| ',' |] |> Seq.toList |> List.map int
        let fields = createFields rest

        // printfn "Fields: %A" fields

        let result = processUntilWinning drawnNumbers fields None

        match result with
        | Some (x, f) -> 
            // printfn "Number: %i, Winning Field:\n%A" x f
            let sum = calculateScore x f
            printfn "Score: %i, drawn number: %i -> total: %i" sum x (sum * x)

        | None -> printfn "No Winner"

    | _ -> printfn "Falscher Input"
    