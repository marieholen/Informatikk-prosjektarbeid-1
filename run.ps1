# ==========================================
# Knitspace build-and-run (frontend + backend)
# Cross-platform using PowerShell background jobs
# ==========================================

Write-Host "=== Starting Knitspace build and run ===" -ForegroundColor Cyan

$root = Get-Location

# ---- Start Backend as JOB ----
Write-Host "`n[1/3] Starting backend server in background job..." -ForegroundColor Yellow

$backendPath = Join-Path $root "knitspace/knitspace-backend"
if (-Not (Test-Path $backendPath)) {
    Write-Host "ERROR: Cannot find backend directory: $backendPath" -ForegroundColor Red
    exit 1
}

$backendJob = Start-Job -ScriptBlock {
    param($path)
    Set-Location $path
    mvn spring-boot:run
} -ArgumentList $backendPath

Write-Host "Backend job started: Job ID $($backendJob.Id)" -ForegroundColor Green


# ---- Build Frontend ----
Write-Host "`n[2/3] Building frontend..." -ForegroundColor Yellow

$frontendPath = Join-Path $root "knitspace/knitspace-frontend"
if (-Not (Test-Path $frontendPath)) {
    Write-Host "ERROR: Cannot find frontend directory: $frontendPath" -ForegroundColor Red
    Stop-Job $backendJob
    exit 1
}

Set-Location $frontendPath

mvn clean compile
if ($LASTEXITCODE -ne 0) {
    Write-Host "Frontend build failed!" -ForegroundColor Red
    Set-Location $root
    Stop-Job $backendJob
    exit 1
}

# ---- Run Frontend ----
Write-Host "`n[3/3] Running JavaFX frontend..." -ForegroundColor Yellow

mvn javafx:run
if ($LASTEXITCODE -ne 0) {
    Write-Host "Frontend failed to start!" -ForegroundColor Red
    Set-Location $root
    Stop-Job $backendJob
    exit 1
}

# ---- After frontend closes ----
Write-Host "`nFrontend closed. Stopping backend..." -ForegroundColor Cyan
Stop-Job $backendJob | Out-Null
Receive-Job $backendJob | Out-Null
Remove-Job $backendJob | Out-Null

# Back to root
Set-Location $root

Write-Host "All processes stopped. Goodbye!" -ForegroundColor Green
