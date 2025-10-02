-- CreateEnum
CREATE TYPE "UserOnProjectStatus" AS ENUM ('REQUESTED', 'ACCEPTED', 'DENIED');

-- CreateTable
CREATE TABLE "projects" (
    "id" TEXT NOT NULL,
    "name" TEXT NOT NULL,
    "updatedAt" TIMESTAMP(3) NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "projects_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "usersOnProjects" (
    "roleId" TEXT NOT NULL,
    "status" "UserOnProjectStatus" NOT NULL DEFAULT 'REQUESTED',
    "userId" TEXT NOT NULL,
    "projectId" TEXT NOT NULL,
    "attachedAt" TIMESTAMP(3),
    "updatedAt" TIMESTAMP(3) NOT NULL,
    "requestedAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "usersOnProjects_pkey" PRIMARY KEY ("userId","projectId")
);

-- AddForeignKey
ALTER TABLE "usersOnProjects" ADD CONSTRAINT "usersOnProjects_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "usersOnProjects" ADD CONSTRAINT "usersOnProjects_projectId_fkey" FOREIGN KEY ("projectId") REFERENCES "projects"("id") ON DELETE CASCADE ON UPDATE CASCADE;
