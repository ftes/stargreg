@echo off
echo Uebersetzung einer tex-datei
echo Parameter: Name der tex-datei OHNE die Endung .tex

SET tex=%1
SET name=%tex:~0,-4%


REM pdflatex %name%.tex
bibtex %name%
makeindex -s %name%.ist -t %name%.alg -o %name%.acr %name%.acn
REM makeindex -s %name%.ist -t %name%.glg -o %name%.gls %name%.glo
REM makeindex -s %name%.ist -t %name%.slg -o %name%.syi %name%.syg
REM pdflatex %name%.tex
REM pdflatex %name%.tex
pdflatex -synctex=-1 %name%.tex -interaction=nonstopmode --src-specials -shell-escape %name%.tex


del %name%.a.*
del %name%.p.*