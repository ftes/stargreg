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
pdflatex %file%.tex

del %file%.acn
del %file%.acr
del %file%.alg
del %file%.aux
del %file%.bbl
del %file%.blg
del %file%.glg
del %file%.glo
del %file%.gls
del %file%.ist
del %file%.lof
del %file%.log
del %file%.lop
del %file%.lot
del %file%.slg
del %file%.syg
del %file%.syi
del %file%.toc
del %file%.url