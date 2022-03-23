<template>
<div class="container">

<div>
  <div style="text-align:center" ><h2>정치</h2></div>

<div class="form-check">
      <input type="checkbox" class="form-check-input" id="check1" name="option1" value="something" checked>
      <label class=" m-1 btn btn-primary" for="check1">청와대</label>

      <input type="checkbox" class="form-check-input" id="check2" name="option2" value="something" checked>
      <label class="m-1 btn btn-primary" for="check2">국회/정당</label>

      <input type="checkbox" class="form-check-input" id="check3" name="option3" value="something" checked>
      <label class="m-1 btn btn-primary" for="check3">북한</label>

      <input type="checkbox" class="form-check-input" id="check4" name="option4" value="something" checked>
      <label class="m-1 btn btn-primary" for="check4">행정</label>

      <input type="checkbox" class="form-check-input" id="check5" name="option5" value="something" checked>
      <label class="m-1 btn btn-primary" for="check5">국방/외교</label>

      <input type="checkbox" class="form-check-input" id="check6" name="option6" value="something" checked>
      <label class="m-1 btn btn-primary" for="check6">정치일반</label>

    </div>

    <div class="form-check">
    </div></div>

</div>
</template>

<script>
export default {
  name: 'MyInterest',
  methods: {
    checked: function () {
    }
  }
}
</script>

<style>
.form-check input{
    display: none;
}
.form-check label {
   color: black;
  background-color: white;
   border-radius: 1rem;
   cursor: pointer;
}
.form-check input:checked + label {
  color: white;
  background-color: #0d6efd;
}
</style>
