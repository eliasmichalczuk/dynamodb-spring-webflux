db.createUser(
        {
            user: "user",
            pwd: "pass",
            roles: [
                {
                    role: "readWrite",
                    db: "mongodb"
                }
            ]
        }
);
db.collection.create('Product')
db.collection.insert('Product', { id: '2cab3698-f905-4aa4-88f4-8bd029826e18', name: 'rice', price: '11.33' })
