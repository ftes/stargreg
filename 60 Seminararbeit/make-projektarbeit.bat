@echo off
echo Uebersetzung einer tex-datei
echo Parameter: Name der tex-datei OHNE die Endung .tex

set file=80_seminararbeit
echo %file%%

pdflatex -shell-escape %file%.tex
bibtex %file%
makeindex -s %file%.ist -t %file%.alg -o %file%.acr %file%.acn
makeindex -s %file%.ist -t %file%.glg -o %file%.gls %file%.glo
makeindex -s %file%.ist -t %file%.slg -o %file%.syi %file%.syg
pdflatex -shell-escape %file%.tex
pdflatex -shell-escape %file%.tex
pdflatex -shell-escape %file%.tex

REM ren %file%.tex tmp%file%.tex
REM ren %file%.pdf tmp%file%.pdf
REM del %file%.*
REM ren tmp%file%.tex %file%.tex
REM ren tmp%file%.pdf %file%.pdf