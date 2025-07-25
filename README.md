🌟 Jewel Paper Book - Backend 🚀
📚 Che cos’è Jewel Paper Book?
Che tu sia un lettore curioso o un autore, Jewel Paper Book è la community perfetta per far brillare la tua passione per i libri! ✨
Il posto dove le storie diventano realtà! Qui puoi leggere, scrivere e condividere racconti che ti faranno emozionare, sognare e divertire. 📖💫
Unisciti a JPB e trasforma le tue idee in storie indimenticabili. 🖋️❤️

📌 Descrizione del progetto
Questo progetto backend è sviluppato in Java (Spring Boot) e utilizza PostgreSQL come database per gestire i dati dell’applicazione Jewel Paper Book.
Espone API REST consumate dal frontend React per permettere la gestione di utenti, storie, autenticazione e altre funzionalità core.

⚠️ IMPORTANTE: Questo backend è fondamentale per far funzionare correttamente il frontend React. I due repository lavorano insieme per creare l’app completa!

🔗 Link al frontend
[Capstone Project Frontend (React + Vite)](https://github.com/Pierattilio-correale/Capstone-Project-Frontend)

🚀 Funzionalità principali
🔐 Gestione autenticazione e sicurezza

🗂️ CRUD su risorse tramite API REST

🛠️ Persistenza dati con PostgreSQL e JPA/Hibernate

⚙️ Architettura modulare e pulita

🛠️ Tecnologie utilizzate
Java ☕

Spring Boot 🌱

PostgreSQL 🐘

JPA / Hibernate 📦

Maven 🔧

🚀 Come avviare il progetto
Clona il repository:
git clone https://github.com/Pierattilio-correale/Capstone-Project.git
Configura il database PostgreSQL (crea il db e aggiorna application.properties o application.yml)

📋 Configurazione ambiente
Per far funzionare correttamente il backend, devi fornire alcune configurazioni sensibili come la password del database PostgreSQL e le chiavi per servizi esterni.

1. Creare il file env.properties
Crea un file chiamato env.properties nella root del progetto (o in una cartella esterna), e inserisci le seguenti proprietà, sostituendo i valori con i tuoi:

properties
postgresql.password=tuo_password_postgres
cloud_name=tuo_cloud_name
api_key=tuo_api_key
api_secret=tuo_api_secret
⚠️ Nota: questo file NON deve essere caricato nel repository Git (aggiungilo a .gitignore).

Costruisci ed esegui il progetto:
4. L’API sarà disponibile su http://localhost:8080 🌐

🤝 Collaborazioni e contributi
Se vuoi contribuire, apri pure una pull request! 🙌

