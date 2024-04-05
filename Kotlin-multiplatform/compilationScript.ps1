# Define the number of times to run the deployment
$numRuns = 5


$kotlinProjectPath = "D:\Escritorio\Tesis2024\Kotlin-multiplatform\KotlinTesisApp"


$logFile = "D:\Escritorio\Tesis2024\Kotlin-multiplatform\deployment-time"


Set-Location -Path $kotlinProjectPath 

for ($i = 1; $i -le $numRuns; $i++) {
    $result = Measure-Command {
        ./gradlew clean assembleRelease

    }

    $totalSeconds = $result.TotalSeconds
    "${totalSeconds} seconds" | Out-File -FilePath $logFile -Append

}

