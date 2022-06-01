using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace BookWorm.Models
{
    public partial class bookwormContext : DbContext
    {
        public bookwormContext()
        {
        }

        public bookwormContext(DbContextOptions<bookwormContext> options)
            : base(options)
        {
        }

        public virtual DbSet<author> author { get; set; }
        public virtual DbSet<book> book { get; set; }
        public virtual DbSet<book_author> book_author { get; set; }
        public virtual DbSet<book_loan> book_loan { get; set; }
        public virtual DbSet<category> category { get; set; }
        public virtual DbSet<reminder> reminder { get; set; }
        public virtual DbSet<user_type> user_type { get; set; }
        public virtual DbSet<users> users { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseMySql("server=localhost;user id=root;database=bookworm", Microsoft.EntityFrameworkCore.ServerVersion.Parse("10.4.24-mariadb"));
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasCharSet("utf16")
                .UseCollation("utf16_hungarian_ci");

            modelBuilder.Entity<book>(entity =>
            {
                entity.Property(e => e.loanable).HasDefaultValueSql("'1'");

                entity.HasOne(d => d.category)
                    .WithMany(p => p.book)
                    .HasForeignKey(d => d.category_id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("book_ibfk_1");
            });

            modelBuilder.Entity<book_author>(entity =>
            {
                entity.HasOne(d => d.author)
                    .WithMany(p => p.book_author)
                    .HasForeignKey(d => d.author_id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("book_author_ibfk_1");

                entity.HasOne(d => d.book)
                    .WithMany(p => p.book_author)
                    .HasForeignKey(d => d.book_id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("book_author_ibfk_2");
            });

            modelBuilder.Entity<book_loan>(entity =>
            {
                entity.HasOne(d => d.book)
                    .WithMany(p => p.book_loan)
                    .HasForeignKey(d => d.book_id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("book_loan_ibfk_2");

                entity.HasOne(d => d.user)
                    .WithMany(p => p.book_loan)
                    .HasForeignKey(d => d.user_id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("user_id_fk");
            });

            modelBuilder.Entity<reminder>(entity =>
            {
                entity.HasOne(d => d.loan_item)
                    .WithMany(p => p.reminder)
                    .HasForeignKey(d => d.loan_item_id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("reminder_ibfk_1");
            });

            modelBuilder.Entity<users>(entity =>
            {
                entity.Property(e => e.language).HasDefaultValueSql("'HU'");

                entity.HasOne(d => d.user_type)
                    .WithMany(p => p.users)
                    .HasForeignKey(d => d.user_type_id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("users_ibfk_1");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
