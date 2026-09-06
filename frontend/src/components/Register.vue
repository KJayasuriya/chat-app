<script setup>
    import { ref } from 'vue';

    const name = ref('')
    const phone = ref('')
    const pwd = ref('')
    const confirm = ref('')
    const emit = defineEmits(['addUser']);
    function signup(){
        if(name.value.trim() === ''){
            alert("Name should NOT be empty!");
            return;
        }
        if(pwd.value.trim() === '' || pwd.value.length < 8){
            alert('Password must have atleast 8 characters!');
            return;
        }
        if(pwd.value !== confirm.value){
            alert('Please confirm the new password correctly');
            return;
        }
        if(phone.value.length < 10){
            alert('Phone must be 10 digits long!');
            return;
        }
        emit('addUser',{
            name: name.value,
            phone: phone.value,
            password: pwd.value
        })
    }
</script>

<template>
    
    <form @submit.prevent="signup">
        <h2>Register</h2>
        <label>User Name</label>
        <input v-model="name" type="text" required>
        <label>Phone Number:</label>
        <input v-model="phone" type = "tel" required>
        <label>Password</label>
        <input v-model="pwd" type="password" required>
        <label>Confirm Password</label>
        <input v-model="confirm" type="password" required>
        <button type="submit">Sign Up</button>
    </form>
</template>

<style scoped>
    form{
        display:flex;
        flex-direction: column;
        margin:auto;
        width:400px;
        justify-content: space-between;
        background: white;
        padding: 15px;
        border-radius: 10px;
        gap:10px;
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
        background-color: #159bc3;
        color:white;
        font-weight: bold;
        width:fit-content;
        padding: 8px 15px;
        border:none;
        border-radius: 10px;
        margin:auto;
        cursor: pointer;
        transition: 0.3s;
    }
    button:active{
        transform: translateY(5px);
    }
</style>