# Data Model (Entities & Relationships)

## High-Level ER Diagram

_Embed/link diagram (Mermaid, draw.io, etc), or describe._

```mermaid 
erDiagram
    USER ||--o{ ORDER : places
    ORDER ||--|{ LINE_ITEM : contains
    USER {
        string id
        string name
    }
    ORDER {
        string orderId
        date orderDate
    }
    LINE_ITEM {
        string sku
        int quantity
        decimal price
    } 
```


## Major Entity Descriptions

| Entity         | Description                | Main Relationships     |
|----------------|---------------------------|-----------------------|
| User           |                           | Order                 |
| Order          |                           | Line_Item, User       |
| Line_Item      |                           | Order                 |
