module Day03

open System
open Util


let parse numberString =
    numberString
    |> Seq.map (fun ch -> if ch = '1' then 1 else -1)
    |> Seq.toList
    


let foldFn l1 l2 =
    List.map2 (fun a b -> a + b) l1 l2


let makeBinary number =
    if number >= 0
    then 1
    else 0


let reduceIntListToInt list =

    let foldFn res next =
        (res <<< 1) + makeBinary next

    List.fold foldFn 0 list







let calcCount bitMask list =
    list |> List.fold (fun acc next -> if (next &&& bitMask) > 0 then acc + 1 else acc - 1) 0

let getMatching keep bitmask item =
    let binaryItem = if item &&& bitmask > 0 then 1 else 0
    if binaryItem = keep then true else false





let rec findValue bitMask up list =
    let bitResult = calcCount bitMask list
    let keep =
        if up
        then (if bitResult >= 0 then 1 else 0)
        else (if bitResult >= 0 then 0 else 1)

    // printfn "[Dir: %b, BitMask: %B, Result: %i, Keep: %i]" up bitMask bitResult keep

    let remaining = list |> List.filter (getMatching keep bitMask)
    match remaining with
    | [item] -> item
    | [] -> 0
    | _ when bitMask > 0 -> findValue (bitMask >>> 1) up remaining
    | _ -> 0



let printBinary list =
    list |> List.iter (fun item -> printfn "%B" item)



let day03 () =
    printfn ("Day 03:\n================")
    let input = readLines "input_03"
    let intInput = input |> List.map (fun i -> (sprintf "0b%s" i) |> int)

    let bitcount = List.head input |> String.length
    let negator = (((2.0 ** float bitcount) |> int )-1) 
    let bitMask = (2.0 ** float (bitcount-1)) |> int

    // printfn ("Input:")
    // printBinary intInput

    // PART 1 ==============
    let startList = [ for _ in 1 .. bitcount -> 0]
    let resultList = input |> List.map parse |> List.fold foldFn startList

    let gamma = reduceIntListToInt resultList
    let epsilon = negator ^^^ gamma

    printfn "Result %A" resultList
    printfn "Gamma: %i, Epsilon: %i -> total: %i" gamma epsilon (gamma * epsilon)



    // PART 2 ==============

    let oxygen = findValue bitMask true intInput
    let co2 = findValue bitMask false intInput

    printfn "Oxygen: %i, CO2: %i, total: %i" oxygen co2 (oxygen * co2)