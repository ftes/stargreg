@echo off
echo Uebersetzung einer tex-datei
echo Parameter: Name der tex-datei OHNE die Endung .tex
pdflatex %1.tex
bibtex %1
makeindex -s %1.ist -t %1.alg -o %1.acr %1.acn
makeindex -s %1.ist -t %1.glg -o %1.gls %1.glo
makeindex -s %1.ist -t %1.slg -o %1.syi %1.syg
pdflatex %1.tex
pdflatex %1.tex
