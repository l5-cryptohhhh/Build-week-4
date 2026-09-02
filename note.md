# Note interne (non su GitHub — vedi .gitignore)

## Commit separati per blocco — sessione 2026-09-02

Non pubblicati (`git push` NON eseguito). Se in futuro chiedi di pushare, verrà fatto
al momento della richiesta, non a quello di creazione dei commit.

Divisi in due gruppi in base alla slide finale della traccia (autenticazione, autorizzazione,
Cliente, Fattura = nucleo; il resto = extra). **Verificato con una compilazione a parte che i
soli commit "necessari" bastano da soli** (senza gli extra dopo).

| Necessari (nucleo della traccia) | Extra (non richiesti dalla slide) |
|---|---|
| `entita fattura` — `entities/Fattura.java` | `entita nota` — `entities/Nota.java` |
| `repository indirizzo` — `IndirizzoRepository` (serve al Cliente per l'indirizzo obbligatorio) | `gestione note` — repo/service/controller Nota + collega la protezione cancellazione in `ClienteService` |
| `gestione clienti` — repo/service/controller Cliente | `gestione indirizzi` — solo service/controller Indirizzo (CRUD dedicato) |
| `fatture e stato fattura` — repo/service/controller Fattura + `StatoFattura` | `dto indirizzo e nota` — `IndirizzoDTO`, `NotaDTO` |
| `repository utenti` — `UtentiRepository` | |
| `dto per le richieste` — Register/Login/LoginResponse/Cliente/Fattura/CambiaStatoFattura DTO | |
| `login e registrazione con jwt` — security + AuthService/AuthController + Utenti (UserDetails) + pom.xml | |
| `gestione errori` — cartella `exceptions/` | |

Nota tecnica: `IndirizzoRepository` è stato spostato nei "necessari" perché il Cliente lo
richiede strutturalmente (indirizzo obbligatorio) anche se il CRUD dedicato all'Indirizzo è
extra. La verifica di cancellazione su `Nota` in `ClienteService.delete()` è stata scorporata:
nei commit necessari il metodo controlla solo le Fatture, il controllo sulle Note è stato
aggiunto dal commit extra `gestione note`.

Ordine nel log: prima tutti i commit "necessari", poi tutti gli "extra" (`git log --oneline`).

## Nota
`BuildWeek4Application.java` risultava già modificato prima di questa sessione (non da noi) —
lasciato fuori da tutti i commit, non toccato.