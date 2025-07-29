# External Dependencies & Integrations

| Dependency/System       | Type(API/DB/Lib)     | Purpose/Usage                  | Auth/Config Reference    |
|-------------------------|----------------------|-------------------------------|-------------------------|
| AuthService             | API                  | User authentication           | OAuth/Spring Security   |
| PaymentProvider         | API                  | Payments integration          | API key/secrets         |
| Redis                   | DB/Cache             | Caching session data          | Redis config            |
| EmailService            | API                  | Email notifications           | SMTP or 3rd-party       |
| ...                     |                      |                               |                         |

## Known Reliability or Integration Notes
_List issues, retry/fallback strategies, or maintenance responsibilities._
