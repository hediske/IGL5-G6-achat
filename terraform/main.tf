
  # -----------------------
  # 1️⃣ Create VPC
  # -----------------------
  resource "aws_vpc" "eks_vpc" {
    cidr_block           = "10.0.0.0/16"
    enable_dns_hostnames = true
    enable_dns_support   = true
    tags = { Name = "eks-vpc" }
  }

  # -----------------------
  # 2️⃣ Create public subnets
  # -----------------------
  resource "aws_subnet" "eks_subnet_a" {
    vpc_id                  = aws_vpc.eks_vpc.id
    cidr_block              = "10.0.1.0/24"
    availability_zone       = "${var.aws_region}a"
    map_public_ip_on_launch = true
    tags = {
      Name                                        = "eks-subnet-a"
      "kubernetes.io/role/elb"                    = "1"
      "kubernetes.io/cluster/${var.cluster_name}" = "shared"
    }
  }

  resource "aws_subnet" "eks_subnet_b" {
    vpc_id                  = aws_vpc.eks_vpc.id
    cidr_block              = "10.0.2.0/24"
    availability_zone       = "${var.aws_region}b"
    map_public_ip_on_launch = true
    tags = {
      Name                                        = "eks-subnet-b"
      "kubernetes.io/role/elb"                    = "1"
      "kubernetes.io/cluster/${var.cluster_name}" = "shared"
    }
  }

  # -----------------------
  # 3️⃣ Internet Gateway
  # -----------------------
  resource "aws_internet_gateway" "igw" {
    vpc_id = aws_vpc.eks_vpc.id
    tags = { Name = "eks-igw" }
  }

  # -----------------------
  # 4️⃣ Route table
  # -----------------------
  resource "aws_route_table" "public" {
    vpc_id = aws_vpc.eks_vpc.id
    route {
      cidr_block = "0.0.0.0/0"
      gateway_id = aws_internet_gateway.igw.id
    }
    tags = { Name = "eks-public-rt" }
  }

  resource "aws_route_table_association" "a" {
    subnet_id      = aws_subnet.eks_subnet_a.id
    route_table_id = aws_route_table.public.id
  }

  resource "aws_route_table_association" "b" {
    subnet_id      = aws_subnet.eks_subnet_b.id
    route_table_id = aws_route_table.public.id
  }

  # -----------------------
  # 5️⃣ Security Group
  # -----------------------
  resource "aws_security_group" "eks_sg" {
    name        = "eks-node-sg"
    description = "Security group for EKS nodes"
    vpc_id      = aws_vpc.eks_vpc.id

    ingress {
      description = "Allow all traffic within VPC"
      from_port   = 0
      to_port     = 65535
      protocol    = "tcp"
      cidr_blocks = ["10.0.0.0/16"]
    }

    egress {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = ["0.0.0.0/0"]
    }

    tags = { Name = "eks-node-sg" }
  }

  # -----------------------
  # 6️⃣ EKS Cluster (Native Resources)
  # -----------------------
  resource "aws_eks_cluster" "main" {
    name     = var.cluster_name
    role_arn = var.lab_role_arn
    version  = "1.29"


    vpc_config {
      subnet_ids              = [aws_subnet.eks_subnet_a.id, aws_subnet.eks_subnet_b.id]
      endpoint_public_access  = true
      endpoint_private_access = false
      security_group_ids      = [aws_security_group.eks_sg.id]
    }

    tags = {
      Environment = "dev"
      Terraform   = "true"
    }
  }

  # -----------------------
  # 7️⃣ EKS Node Group
  # -----------------------
  resource "aws_eks_node_group" "main" {
    cluster_name    = aws_eks_cluster.main.name
    node_group_name = var.node_group_name
    node_role_arn   = var.lab_role_arn
    subnet_ids      = [aws_subnet.eks_subnet_a.id, aws_subnet.eks_subnet_b.id]

    scaling_config {
      desired_size = var.desired_capacity
      max_size     = 3
      min_size     = 1
    }

    instance_types = [var.node_instance_type]

    tags = {
      Environment = "dev"
      Terraform   = "true"
    }

    depends_on = [aws_eks_cluster.main]
  }
