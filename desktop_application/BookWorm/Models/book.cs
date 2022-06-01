using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

#nullable disable

namespace BookWorm.Models
{
    [Index(nameof(barcode), Name = "barcode", IsUnique = true)]
    [Index(nameof(category_id), Name = "categoryindex")]
    [Index(nameof(loanable), Name = "loanable")]
    [Index(nameof(title), Name = "title")]
    public partial class book
    {
        public book()
        {
            book_author = new HashSet<book_author>();
            book_loan = new HashSet<book_loan>();
        }

        [Key]
        [Column(TypeName = "int(11)")]
        public int id { get; set; }
        [Required]
        [StringLength(200)]
        public string title { get; set; }
        [StringLength(50)]
        public string isbn { get; set; }
        [StringLength(500)]
        public string img_link { get; set; }
        [StringLength(2000)]
        public string short_description { get; set; }
        [Required]
        public bool? loanable { get; set; }
        [Column(TypeName = "int(11)")]
        public int max_loan_days { get; set; }
        [Column(TypeName = "int(11)")]
        public int category_id { get; set; }
        [Required]
        [StringLength(50)]
        public string barcode { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime created_date { get; set; }
        [Required]
        [StringLength(100)]
        public string created_by { get; set; }
        [StringLength(100)]
        public string last_mod_user { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? last_mod_date { get; set; }

        [ForeignKey(nameof(category_id))]
        [InverseProperty("book")]
        public virtual category category { get; set; }
        [InverseProperty("book")]
        public virtual ICollection<book_author> book_author { get; set; }
        [InverseProperty("book")]
        public virtual ICollection<book_loan> book_loan { get; set; }
    }
}
