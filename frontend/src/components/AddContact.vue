<script setup>
    import {ref} from 'vue'
    const API_URL = 'https://chat-app-backend-9mfo.onrender.com'
    const props = defineProps({
        currentUser:String
    })
    const username = ref('');
    const emit = defineEmits(['addContact','cancel'])
    async function addContact(){
        if(username.value.trim() === ''){
            alert("Username should NOT be empty!");
            return;
        }
        
        try{
            const response = await fetch(`${API_URL}/api/contacts`,{
                method:'POST',
                headers:{
                    'Content-Type':'application/json'
                },
                body:JSON.stringify({
                    owner:props.currentUser,
                    username:username.value.trim()
                })
            })
            if(!response.ok){
                const error = await response.text();
                alert(error);
                return;
            }
            const contact = await response.json();
            emit('addContact',contact);
            username.value = '';
        }
        catch(error){
            console.error(error);
            alert('Unable to connect to server');
        }
    }
</script>

<template>
    <form class="contact-form" @submit.prevent="addContact">
        <label>Username: 
            <input type ="text" placeholder="e.g. Arun" v-model="username" required>
        </label>
        <button type = "submit">Add</button>
        <button type="button" @click="$emit('cancel')">Cancel</button>
    </form>
</template>

<style scoped>
    .contact-form{
        display: flex;
        justify-content: space-between;
        background: white;
        padding: 15px;
        border: 0.5px solid black;
        width:fit-content;
        margin:auto;
        border-radius: 10px;
        gap:10px;
        color:black;
    }
    input{
        border-radius: 2px;
        border: .5px solid black;
        outline:none;
        padding:5px;
    }
    input:focus{
        border:none;
        outline: 1px solid #00a944;     
    }
    button{
        color:black;
        font-weight: bold;
        width:fit-content;
        padding: 8px 15px;
        background-color:white;
        border-radius: 10px;
        margin:auto;
        cursor: pointer;
    }
    button[type = "submit"]{
        border:none;
        background-color: #159bc3;
    }
    button[type = "submit"]:hover{
        background-color: #00a944;
        transition: 0.3s;
    }
    button[type = "submit"]:active{
        background-color: #00a944;
        transform: scale(0.95);
    }
</style>