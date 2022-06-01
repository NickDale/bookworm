using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

#nullable disable

namespace BookWorm.Models
{
    [Index(nameof(name), Name = "name", IsUnique = true)]
    public partial class category
    {
        public category()
        {
            book = new HashSet<book>();
        }

        [Key]
        [Column(TypeName = "int(11)")]
        public int id { get; set; }
        [Required]
        [StringLength(100)]
        public string name { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime created_date { get; set; }
        [Required]
        [StringLength(100)]
        public string created_by { get; set; }
        [StringLength(100)]
        public string last_mod_user { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? last_mod_date { get; set; }

        [InverseProperty("category")]
        public virtual ICollection<book> book { get; set; }
    }
}
