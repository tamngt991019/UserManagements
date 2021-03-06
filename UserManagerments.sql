USE [master]
GO
/****** Object:  Database [UserManagements]    Script Date: 4/17/2020 2:20:21 PM ******/
CREATE DATABASE [UserManagements]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'J3LP0001', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\J3LP0001.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'J3LP0001_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\J3LP0001_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [UserManagements] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [UserManagements].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [UserManagements] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [UserManagements] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [UserManagements] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [UserManagements] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [UserManagements] SET ARITHABORT OFF 
GO
ALTER DATABASE [UserManagements] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [UserManagements] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [UserManagements] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [UserManagements] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [UserManagements] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [UserManagements] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [UserManagements] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [UserManagements] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [UserManagements] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [UserManagements] SET  DISABLE_BROKER 
GO
ALTER DATABASE [UserManagements] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [UserManagements] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [UserManagements] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [UserManagements] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [UserManagements] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [UserManagements] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [UserManagements] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [UserManagements] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [UserManagements] SET  MULTI_USER 
GO
ALTER DATABASE [UserManagements] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [UserManagements] SET DB_CHAINING OFF 
GO
ALTER DATABASE [UserManagements] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [UserManagements] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [UserManagements] SET DELAYED_DURABILITY = DISABLED 
GO
USE [UserManagements]
GO
/****** Object:  Table [dbo].[tblPromotions]    Script Date: 4/17/2020 2:20:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblPromotions](
	[ProID] [varchar](50) NOT NULL,
	[ProName] [nvarchar](50) NULL,
	[Description] [nvarchar](50) NULL,
 CONSTRAINT [PK_tblPromotionDetails] PRIMARY KEY CLUSTERED 
(
	[ProID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblPromotionsDetails]    Script Date: 4/17/2020 2:20:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblPromotionsDetails](
	[UserID] [varchar](50) NOT NULL,
	[ProID] [varchar](50) NOT NULL,
	[Ranks] [int] NULL,
	[Date] [date] NULL,
 CONSTRAINT [PK_tblPromotionsDetails] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC,
	[ProID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRoles]    Script Date: 4/17/2020 2:20:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRoles](
	[RoleID] [varchar](50) NOT NULL,
	[RoleName] [nvarchar](50) NULL,
 CONSTRAINT [PK_Roles] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblUsers]    Script Date: 4/17/2020 2:20:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUsers](
	[UserID] [varchar](50) NOT NULL,
	[UserName] [nvarchar](50) NULL,
	[Password] [varchar](1000) NULL,
	[RoleID] [varchar](50) NULL,
	[Email] [varchar](100) NULL,
	[Phone] [varchar](50) NULL,
	[Photo] [varchar](1000) NULL,
	[Status] [bit] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblPromotions] ([ProID], [ProName], [Description]) VALUES (N'P001', N'Sale25', N'Sale 25 percent')
INSERT [dbo].[tblPromotions] ([ProID], [ProName], [Description]) VALUES (N'P002', N'Sale50', N'Sale 50 percent')
INSERT [dbo].[tblPromotions] ([ProID], [ProName], [Description]) VALUES (N'P003', N'Sale75', N'Sale 75 percent')
INSERT [dbo].[tblPromotionsDetails] ([UserID], [ProID], [Ranks], [Date]) VALUES (N'buianhtuan', N'P002', 7, CAST(N'2020-01-19' AS Date))
INSERT [dbo].[tblPromotionsDetails] ([UserID], [ProID], [Ranks], [Date]) VALUES (N'hienho', N'P002', 10, CAST(N'2020-01-19' AS Date))
INSERT [dbo].[tblPromotionsDetails] ([UserID], [ProID], [Ranks], [Date]) VALUES (N'hienho', N'P003', 5, CAST(N'2020-01-19' AS Date))
INSERT [dbo].[tblRoles] ([RoleID], [RoleName]) VALUES (N'AD', N'ADMIN')
INSERT [dbo].[tblRoles] ([RoleID], [RoleName]) VALUES (N'UR', N'USER')
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'ASD', N'ASD', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'AD', N'asdasdasd@sdfgsdf.as', N'0123456789', N'default.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'buianhtuan', N'BUI ANH TUAN', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'UR', N'anhtuanbui@gmail.com', N'0321321321', N'buianhtuan.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'dienquan', N'DIEN QUAN ENTERTAINMENTS', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'AD', N'dienquan456@gmail.com', N'0456654456', N'dienquan.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'hienho', N'HIEN HO', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'AD', N'hienho123123@gmail.com', N'0456456456', N'hienho.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'qwe123', N'FANG QING', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'UR', N'fqfqfq123@gasda.co.in', N'0123123000', N'default.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'sontungmtp', N'SON TUNG MTP', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'UR', N'sontungmtp@gmail.com', N'0123456789', N'default.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'test', N'TESTTTTT', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'AD', N'testing@aaa.com', N'0124356879', N'default.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'tranthanh', N'TRAN THANH', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'UR', N'tranthanh123@gmail.com', N'0123321123', N'tranthanh.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'trucnhan', N'TRUC NHAN', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'UR', N'trucnhan321@gmail.com', N'0123123123', N'default.jpg', 0)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'truonggiang', N'TRUONG GIANG', N'75263518707598184987916378021939673586055614731957507592904438851787542395619', N'UR', N'truonggiang789@gmail.com', N'0789987789', N'truonggiang.jpg', 1)
INSERT [dbo].[tblUsers] ([UserID], [UserName], [Password], [RoleID], [Email], [Phone], [Photo], [Status]) VALUES (N'user', N'USERRRR', N'64042235640975155117310274771932083755136637533499687061408327255432019864722', N'UR', N'user123@gmail.com', N'0918273645', N'default.jpg', 1)
ALTER TABLE [dbo].[tblPromotionsDetails]  WITH CHECK ADD  CONSTRAINT [FK_tblPromotionsDetails_tblPromotions] FOREIGN KEY([ProID])
REFERENCES [dbo].[tblPromotions] ([ProID])
GO
ALTER TABLE [dbo].[tblPromotionsDetails] CHECK CONSTRAINT [FK_tblPromotionsDetails_tblPromotions]
GO
ALTER TABLE [dbo].[tblPromotionsDetails]  WITH CHECK ADD  CONSTRAINT [FK_tblPromotionsDetails_tblUsers] FOREIGN KEY([UserID])
REFERENCES [dbo].[tblUsers] ([UserID])
GO
ALTER TABLE [dbo].[tblPromotionsDetails] CHECK CONSTRAINT [FK_tblPromotionsDetails_tblUsers]
GO
ALTER TABLE [dbo].[tblUsers]  WITH CHECK ADD  CONSTRAINT [FK_Users_Roles] FOREIGN KEY([RoleID])
REFERENCES [dbo].[tblRoles] ([RoleID])
GO
ALTER TABLE [dbo].[tblUsers] CHECK CONSTRAINT [FK_Users_Roles]
GO
USE [master]
GO
ALTER DATABASE [UserManagements] SET  READ_WRITE 
GO
