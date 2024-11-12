db.createUser(
    {
        user: "csabino",
        pwd: "123456",
        roles: [
            {
                role: "readWrite",
                db: "rinha_db"
            }
        ]
    }
)

db.clientes.insertMany([
    { _id: 1, limite: 100000, saldo: 0 },
    { _id: 2, limite: 80000, saldo: 0 },
    { _id: 3, limite: 1000000, saldo: 0 },
    { _id: 4, limite: 10000000, saldo: 0 },
    { _id: 5, limite: 500000, saldo: 0 }
]);
