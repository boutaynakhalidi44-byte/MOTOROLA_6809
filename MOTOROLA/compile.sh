#!/bin/bash
echo "Compilation du projet motorola..."
echo

cd "$(dirname "$0")"

if [ ! -d "bin" ]; then
    mkdir -p bin
fi

echo "Compilation des fichiers Java..."
javac -d bin -sourcepath src src/motorola/**/*.java

if [ $? -eq 0 ]; then
    echo
    echo "Compilation réussie!"
    echo "Les fichiers compilés sont dans le dossier bin/"
else
    echo
    echo "Erreur lors de la compilation!"
    exit 1
fi

