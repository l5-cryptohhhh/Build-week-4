# Build-week-4

Backend Spring Boot per la gestione di clienti, indirizzi, fatture e note di un'azienda di energia. Autenticazione JWT, autorizzazione basata su ruolo.

## Avvio

```
./mvnw spring-boot:run
```

API su `http://localhost:3001`, DB Postgres `Build-week-4` (config in `src/main/resources/application.properties`).

## Postman

Collection in `Documentazione/Build-week-4.postman_collection.json`.

## Ruoli

`USER`, `COMMERCIALE`, `CONTABILE`, `ADMIN`. Ogni utente registrato via `POST /auth/register` riceve il ruolo `USER` di default — non esiste un endpoint per cambiarlo. Per usare gli endpoint protetti (Clienti, Indirizzi, Fatture) va promosso a mano sul database prima del login:

```sql
UPDATE utenti SET ruolo = 'ADMIN' WHERE email = 'admin@example.com';
```

`POST /clienti` con un utente `ADMIN` richiede anche il `commercialeId` del cliente nel body (con `COMMERCIALE` viene assegnato automaticamente il richiedente stesso, vedi `ClienteService.resolveCommerciale`). Serve quindi un secondo utente promosso a `COMMERCIALE` da usare come id.
