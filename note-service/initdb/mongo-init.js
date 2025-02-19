print("🟢 Début de l'initialisation MongoDB...");

db = connect("mongodb://note-db:27017/medilabo");

print("🟢 Connexion établie avec MongoDB !");

const fs = require('fs');  // Utilise 'fs' pour lire les fichiers
const dataFile = '/docker-entrypoint-initdb.d/data.json';


// Lit le fichier JSON avec fs
try {
    const data = JSON.parse(fs.readFileSync(dataFile, 'utf8'));

    print(`🟢 Données chargées depuis ${dataFile}, insertion en cours...`);

    db.notes.drop();
    db.notes.insertMany(data);

    print("✅ Données insérées avec succès !");
} catch (e) {
    print(`❌ Erreur lors de l'import des données : ${e}`);
}
