$current_path = Get-Location

$documents_path = $args[0]


$current_path_doc = Join-Path -Path $current_path -ChildPath "*.doc"
Copy-Item $current_path_doc -Destination $documents_path -Recurse

java -jar InspectorDoc.jar $documents_path

cd $documents_path

$files = Get-ChildItem $documents_path -Filter *.emf -file -Recurse | 
foreach-object {
    $Source = $_.FullName
    $test = [System.IO.Path]::GetDirectoryName($source)
    $base= $_.BaseName+".jpg"
    $basedir = $test+"\"+$base
    Write-Host $basedir
    Add-Type -AssemblyName system.drawing
    $imageFormat = "System.Drawing.Imaging.ImageFormat" -as [type]
    $image = [drawing.image]::FromFile($Source)
    $image.Save($basedir, $imageFormat::jpeg)
    $image.Dispose()
}

cd Courbes

rm *.emf

cd ..

cd ..

$arguments = $documents_path, 'images'

java -jar InspectorDoc.jar $arguments 