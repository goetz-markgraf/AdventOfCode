module Day02

open Util

let split (input: string) =
    let cmdList = input.Split [|' '|] |> Seq.toList
    (cmdList[0], cmdList[1] |> int)



let foldFn_01 (forward, depth) next =
    let (command, amount) = split next
    let (forwardAdder, depthAdder) =
        match command with
        | "forward" -> (amount, 0)
        | "down" -> (0, amount)
        | "up" -> (0, -amount)
        | _ -> (0,0)

    (forward + forwardAdder, depth + depthAdder)


let foldFn_02 (forward, depth, aim) next =
    let (command, amount) = split next
    let (forwardAdder, depthAdder, aimAdder) =
        match command with
        | "down" -> (0, 0, amount)
        | "up" -> (0, 0, -amount)
        | "forward" -> (amount, aim*amount, 0)
        | _ -> (0,0,0)

    (forward + forwardAdder, depth + depthAdder, aim + aimAdder)




let day02 () =
    printfn ("Day 02:\n================")

    let input = readLines "input_02"

    let (forward, depth) = List.fold foldFn_01 (0, 0) input
    printfn "Result 1: %A -> total: %i" (forward, depth) (forward * depth)

    let (forward', depth', aim') = List.fold foldFn_02 (0,0,0) input
    printfn "Result 2: %A -> total: %i" (forward', depth') (forward' * depth')