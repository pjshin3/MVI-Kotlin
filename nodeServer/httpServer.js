const express = require('express');
const app = express();
let users= [
  {
    id: 12345678,
    name: 'philip'
  }
]
let data = {
    id: 'shinpilje',
    name: 'philip',
    item : 'mac',
    title : 'title',
    test : 'true',
    wife : 'hong',
    age : 30
}
let myAppServerChack =
  {
    version: 2.0
  }


app.get('/users',(req,res)=>{
  console.log('who get in here post/users');
  res.json(users)
});

app.get('/myAppServerChack',(req,res)=>{
  console.log('servserinfo sand');
  res.json(myAppServerChack)
});

app.listen(3000, () =>{
  console.log('Example app listening on post 3000!');
});
