@echo off
echo Uebersetzung einer tex-datei
echo Parameter: Name der tex-datei OHNE die Endung .tex

SET tex=%1
SET name=%tex:~0,-4%


pdflatex %name%.tex
bibtex %name%
makeindex -s %name%.ist -t %name%.alg -o %name%.acr %name%.acn
makeindex -s %name%.ist -t %name%.glg -o %name%.gls %name%.glo
makeindex -s %name%.ist -t %name%.slg -o %name%.syi %name%.syg
pdflatex %name%.tex
pdflatex %name%.tex
pdflatex -synctex=-1 %name%.tex -interaction=nonstopmode --src-specials -shell-escape %name%.tex

REM ren %name%.tex tmp%name%.tex
REM ren %name%.pdf tmp%name%.pdf
REM del %name%.*
REM ren tmp%name%.tex %name%.tex
REM ren tmp%name%.pdf %name%.pdf