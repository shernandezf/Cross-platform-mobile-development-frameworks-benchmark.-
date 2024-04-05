
$numRuns = 5


$flutterprojectPath = ".\flutter_tesis_app"


$logFile = ".\deployment-time"


Set-Location -Path $flutterprojectPath 

for ($i = 1; $i -le $numRuns; $i++) {
    $result = Measure-Command {
        Measure-Command { flutter build apk }

    }

    $totalSeconds = $result.TotalSeconds
    "${i}: ${totalSeconds} seconds" | Out-File -FilePath $logFile -Append

}


Invoke-Item $logFile
