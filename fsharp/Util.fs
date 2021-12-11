module Util

let readLines filePath =
 System.IO.File.ReadLines(filePath) |> Seq.toList
