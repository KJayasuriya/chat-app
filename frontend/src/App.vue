<script setup>
  const API_URL = 'https://chat-app-backend-9mfo.onrender.com'
  import {ref, computed} from 'vue'
  import Register from './components/Register.vue';
  import AddContact from './components/AddContact.vue';
  import ChatWindow from './components/ChatWindow.vue';
  import Login from './components/Login.vue';
  async function getUser(data){
    try{
      const response = await fetch(`${API_URL}/api/users`,{
        method:'POST',
        headers:{
          'Content-Type':'application/json'
        },
        body:JSON.stringify(data)
      })
      if(!response.ok){
        const error = await response.text();
        alert(error);
        return;
      }
      const user = await response.json();
      alert('Registration successful! Please login to continue.');
      showLogin.value = true;
      seeChat.value = false;
    }
    catch(error){
      console.error(error)
      alert('Unable to connet to server')
    }
  }
  const currentUser = ref('');
  const contacts = ref([]);
  function getContacts(data){
    contacts.value.push(data);
    addContact.value = false;
  }
  const seeChat = ref(false);
  const mobileChat = ref(false);
  const addContact = ref(false);
  const showRegisterForm = computed(()=>!showLogin.value && !seeChat.value && !addContact.value);
  const showLogin = ref(true);
  async function login(data){

    try{

        const response = await fetch(`${API_URL}/api/users/login`,{
            method:'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify(data)
        })

        if(!response.ok){
            const error = await response.text();
            alert(error);
            return;
        }

        const user = await response.json();

        currentUser.value = user.username;
        conversations.value = {};

        await loadContacts();

        showLogin.value = false;
        seeChat.value = true;

    }catch(error){

        console.error('Error during login:', error);
        alert('An error occurred during login. Please try again.');

    }
}

function logout(){

    showLogin.value = true;
    seeChat.value = false;
    addContact.value = false;
    mobileChat.value = false;
    selectedContact.value = null;
    currentUser.value = '';
    conversations.value = {};
}

  async function loadContacts(){
    try{
      const response = await fetch(`${API_URL}/api/contacts/${encodeURIComponent(currentUser.value)}`)
      if(!response.ok){
        alert('Unable to load contacts');
        return;
      }
      const data = await response.json();
      contacts.value = data;
      
      await Promise.all(
        data.map(async (contact) =>{
          const response = await fetch(
            `${API_URL}/api/chat/${encodeURIComponent(currentUser.value)}/${encodeURIComponent(contact.username)}`
          )
          if(response.ok){
            conversations.value[contact.username] = await response.json();
          }
        })
      )
    }
    catch(error){
      console.error(error);
      alert('Unable to connect to server')
    }
  }
  function showLoginForm(){
      showLogin.value = true;
      seeChat.value = false;
      addContact.value = false;
  }
  
  const selectedContact = ref(null);
  function selectContact(contact){
    selectedContact.value = contact;
    mobileChat.value = true;
  }
  const conversations = ref({})
  function getLastMessage(contact){
      const messages = conversations.value[contact.username] || [];
      if(messages.length === 0){
        return 'No messages yet'
      }
      return messages[messages.length - 1].message;
  }
  
</script>

<template>
  <div id="app">
    <nav>
      <h1 v-if="showLogin">Login</h1>
      <h1 v-else-if="showRegisterForm">Register/Sign Up</h1>
      <h1 v-else-if="addContact">Add Contact</h1>
      <h1 v-else>ChatLoop</h1>

    <div v-if="!showLogin && seeChat">
      <button @click="addContact = true" class="contact-btn">Add Contact</button>
      <button class="log-out-btn" @click="logout">Log Out</button>
    </div>
    </nav>   
    <Login v-if = "showLogin" @login="login" @register="seeChat = false; showLogin = false"/>
    <Register v-if="showRegisterForm" @addUser="getUser" @login="showLoginForm"/>    
    <AddContact v-if = "addContact === true" :currentUser="currentUser" @addContact="getContacts" @cancel="addContact = false"/>
    <main v-if="!showLogin && seeChat && !addContact" :class="{ 'mobile-chat-active': mobileChat }">
    <div class="contact-list">
        <div
            v-for="contact in contacts"
            :key="contact.username"
            class="contact"
            @click="selectContact(contact)">
            <div class="dp">👤</div>
            <div class="contact-info">
              <h3>{{ contact.username }}</h3>
              <p>{{getLastMessage(contact)}}</p>
            </div>
        </div>
    </div>
    <ChatWindow
    id="chat-window"
    :contact="selectedContact"
    :currentUser="currentUser"
    :conversations="conversations"
    @back="mobileChat = false"
    />
  </main>
  </div>
</template>

<style scoped>
  
  #app{
    --accent-color: blue;
    display:flex;
    /* justify-content: space-between; */
    flex-direction: column;
    /* gap:50px; */
    background-size: cover;
    height: 100vh;
    overflow:hidden;
  }
  h1{
    font-size: 2rem;
  }
  h2{
    font-size: 1.5rem;
  }
  h3{
    font-size: 1.2rem;
  }
  p{
    font-size: 1rem;
  }
  .contact-btn{
    
    background-color:#22c55e;
    width:fit-content;
    cursor: pointer;
    border:none;
    border-radius: 10px;
    padding:10px;
    color:black;
    font-weight: bold;
    transition:0.3s;
  }
  .contact-btn:hover{
    background:#16a34a;
  }
  .contact-btn:active{
    transform: scale(.8);
  }
  .dp{
    background-color: hsl(from var(--accent-color) h s calc(l + 40));
    padding: 1rem;
    border-radius: 50%;
  }
  .contact{
    padding: 20px;
    border-bottom: .5px solid #00000075;
    display:flex;
    align-items: center;
    gap: 20px;
  }
  main{
    display: flex;
    background: white;
    padding: 15px; 
    flex:1;
    overflow:hidden;
    min-height:0; 
  }
  
  .contact-list{
    display:flex;
    background: white;
    flex-direction:column;
    padding: 15px;
    width:40%;
    border-radius: 10px;
    gap:10px;
    color:black;
    cursor:pointer;
    transition:.3s;
  }
  .contact:active{
    transition: 0.3s;
    background-color: hsl(from #16a34a h s calc(l + 30)/0.3);
  }
  nav{
    display:flex; 
    justify-content: space-between;
    align-items:center;
    background-color:#172033;
    color:white;
    padding: 15px;
    gap: 20px;
  }
  .log-out-btn{
    background-color: oklch(62.793% 0.25768 29.223);
    color:white;
    font-weight: bold;
    width:fit-content;
    padding: 8px 15px;
    border:none;
    border-radius: 10px;
    cursor: pointer;  
  }
  .log-out-btn:active{
    opacity:0.6;
  }

  @media (max-width: 768px){
    nav{
      padding: 10px;
      gap: 8px;
    }
    nav h1{
      font-size: 1.5rem;
    }
    .contact-btn,.log-out-btn{
      padding: 7px 10px;
      font-size: 0.8rem;
    }
    
      main{
          padding: 0;
          width: 100%;
          flex: 1;
      }

      .contact-list{
          width: 100%;
          padding: 0;
          border-radius: 0;
      }

      #chat-window{
          width: 100%;
          min-width: 0;
          display: none;
      }

    main.mobile-chat-active .contact-list{
        display: none;
    }

    main.mobile-chat-active #chat-window{
        display: flex;
    }
  } 
</style>
