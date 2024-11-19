<template>
  <div class="sidebar">
    <ul>
      <RouterLink :to="{ name: 'main' }" class="no-decoration">
        <li>
          <font-awesome-icon :icon="['fas', 'house']" size="2xl"/>
          <span>Home</span>
        </li>
      </RouterLink>
      <RouterLink :to="{ name: 'map' }" class="no-decoration">
        <li>
          <font-awesome-icon :icon="['fas', 'map']" size="2xl"/>
          <span>Map</span>
        </li>
      </RouterLink>
      <li>
        <font-awesome-icon :icon="['fas', 'bookmark']" size="2xl"/>
        <span>Saved</span>
      </li>
      <li>
        <font-awesome-icon :icon="['fas', 'user']" size="2xl"/>
        <span>MyPage</span>
      </li>
    </ul>
  </div>
</template>

<script>
// export default {
//   name: 'Sidebar',
// };
</script>

<style scoped>
.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 80px;
  height: 100vh;
  background-color: #f4f4f4;
  box-sizing: border-box;
  overflow-y: auto;
  z-index: 100;
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

li {
  margin: 20px 0;
  display: flex;
  align-items: center;
  flex-direction: column;
}

span {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}

.no-decoration {
  text-decoration: none;
  color: black;
}
</style>
