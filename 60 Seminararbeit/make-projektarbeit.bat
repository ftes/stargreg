@echo off
echo Uebersetzung einer tex-datei
echo Parameter: Name der tex-datei OHNE die Endung .tex

set file=80_seminararbeit
echo %file%%

pdflatex %file%.tex
bibtex %file%
makeindex -s %file%.ist -t %file%.alg -o %file%.acr %file%.acn
makeindex -s %file%.ist -t %file%.glg -o %file%.gls %file%.glo
makeindex -s %file%.ist -t %file%.slg -o %file%.syi %file%.syg
pdflatex %file%.tex
pdflatex %file%.tex
pdflatex -shell-escape %file%.tex

ren %file%.tex tmp%file%.tex
ren %file%.pdf tmp%file%.pdf
del %file%.*
ren tmp%file%.tex %file%.tex
ren tmp%file%.pdf %file%.pdf