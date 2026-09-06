<script setup>
  import {ref, computed} from 'vue'
  import Register from './components/Register.vue';
  import AddContact from './components/AddContact.vue';
  import ChatWindow from './components/ChatWindow.vue';
  const users = ref([]);
  function getUser(data){
    users.value.push(data);
    seeChat.value = true;
  }
  const currentUser = ref('');
  const contacts = ref([]);
  function getContacts(data){
    contacts.value.push(data);
    addContact.value = false;
  }
  const seeChat = ref(true);
  const addContact = ref(false);
  const showRegisterForm = computed(()=>!seeChat.value && !addContact.value);
  const selectedContact = ref(null);
  function selectContact(contact){
    selectedContact.value = contact;
  }
  const conversations = ref(
    JSON.parse(localStorage.getItem('conversations')) || {}
  )
  function getLastMessage(contact){
      const messages = conversations.value[contact.username] || [];
      if(messages.length === 0){
        return 'No messages yet'
      }
      return messages[messages.length - 1].text;
  }
</script>

<template>
  <div id="app">
    <nav>
      <h1 v-if="addContact === true">Add Contact</h1>
      <h1 v-else-if="showRegisterForm">Register/Sign Up</h1>
      <h1 v-else>Chat App</h1>
      
      <button class="log-out-btn">Log Out</button>
    </nav>    
    <Register v-if = "showRegisterForm" @addUser="getUser"/>
    <AddContact v-if = "addContact === true" :currentUser="currentUser" @addContact="getContacts"/>
    <main>
    <div class="contact-list">
        <div
            v-for="contact in contacts"
            :key="contact.username"
            class="contact"
            @click="selectContact(contact)"
        >
            <h3>{{ contact.username }}</h3>
            <p>{{getLastMessage(contact)}}</p>
            <small><strong>{{ contact.phone }}</strong></small>
        </div>
    </div>

    <ChatWindow id = "chat-window" :contact="selectedContact" :conversations = "conversations"/>
</main>
    <button @click.prevent="addContact = true" class="contact-btn">👤+</button>
  </div>
</template>

<style scoped>
  
  #app{
    display:flex;
    /* justify-content: space-between; */
    flex-direction: column;
    /* gap:50px; */
    background-size: cover;
    min-height: 100vh;
  }
  .contact-btn{
    background-color: oklch(87.094% 0.26034 146.803);
    width:fit-content;
    aspect-ratio: 1;
    position:fixed;
    right:10px;
    bottom:10px;
    cursor: pointer;
    border:none;
    border-radius: 50%;
    padding:15px;
    color:white;
    font-weight: bold;
    font-size: 1.5rem;
    transition:0.3s;
  }
  .contact-btn:hover{
    background-color: oklch(87.094% 0.26034 146.803 / .8);
  }
  .contact-btn:active{
    transform: scale(.8);
  }
  .contact{
    padding: 20px;
    border-bottom: .5px solid #00000075;
  }
  main{
    display: flex;
    background: white;
    padding: 15px;  
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
  .contact-list:active{
    opacity:0.8;
  }
  nav{
    display:flex; 
    justify-content: space-between;
    background-color: oklch(87.094% 0.26034 146.803);
    color:white;
    padding: 15px;
  }
  .log-out-btn{
    background-color: oklch(62.793% 0.25768 29.223);
    color:white;
    font-weight: bold;
    width:fit-content;
    padding: 8px 15px;
    border:none;
    border-radius: 10px;
    margin:auto;
    cursor: pointer;  
  }
  .log-out-btn:active{
    opacity:0.6;
  }
</style>
