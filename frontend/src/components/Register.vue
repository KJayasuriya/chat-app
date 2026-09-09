<script setup>
    import { ref } from 'vue';

    const username = ref('')
    const pwd = ref('')
    const confirm = ref('')
    const emit = defineEmits(['addUser', 'login']);
    function signup(){
        if(username.value.trim() === ''){
            alert("username should NOT be empty!");
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
        emit('addUser',{
            username: username.value,
            password: pwd.value
        })
        username.value = '';
        pwd.value = '';
        confirm.value = '';
    }
</script>

<template>
    
    <form @submit.prevent="signup">
        <h2>Register</h2>
        <label>User name</label>
        <input v-model="username" type="text" required>
        <label>Password</label>
        <input v-model="pwd" type="password" required>
        <label>Confirm Password</label>
        <input v-model="confirm" type="password" required>
        <button type="submit">Sign Up</button>
        <p>
            Already have an account?
            <button type="button" @click="emit('login')">Login</button>
        </p>
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
        box-shadow: 2px 5px 10px rgba(0,0,0,0.2);
        margin:auto;
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
    form>button{
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
    form>button:active{
        transform: translateY(5px);
    }
    p>button{
        background:none;
        border:none;
        color:#159bc3;
        font-weight: bold;
        cursor:pointer;
    }
</style>