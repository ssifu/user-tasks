# APIs
## Authentication APIs:
### Register
- **URL:** `/auth/register`
- **Method:** `POST`
- **Request Body:**
```json
{
    "username": username,
    "password": password,
    "roles": ["ROLE1", "ROLE2", ...]
}
```
### Login
- **URL:** `/auth/login`
- **Method:** `POST`
- **Request Body:**
```json
{
    "username": username,
    "password": password
}
```

## User APIs:
## Tasks of a specific user
- **URL:** `/users/tasks`
- **Method:** `GET`

## Admin APIs:
### Get all the users
- **URL:** `/admin/users`
- **Method:** `GET`

### Create an admin account
- **URL:** `/admin/create`
- **Method:** `POST`
- **Request Body:**
```json
{
    "username": username,
    "password": password,
    "roles": ["ROLE1", "ROLE2", ...]
}
```

### Update user to admin
- **URL:** `/admin/update-user?user_id=userId`
- **Method:** `PUT`

## Task APIs:
### Get all the tasks (ADMIN)
- **URL:** `/tasks/`
- **Method:** `GET`

### Create a task (ADMIN can assign the task to anyone and USER can only create task for themselves)
- **URL:** `/tasks`
- **Method:** `POST`
- **Request Body:**
```json
{
    "taskId": "1",
    "assignedTo": "samiulsifat",
    "title": "title",
    "description": "desciption",
    "dueDate": "01/01/2025"
}
```

### Assign a task (ADMIN)
- **URL:** `/tasks/assign?task_id=taskId&username=username`
- **Method:** `PUT`

### Update a task status
- **URL:** `/tasks/update/status?task_id=taskId&username=username`
- **Method:** `PUT`
