using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

#nullable disable

namespace BookWorm.Models
{
    [Index(nameof(author_id), Name = "author_id")]
    [Index(nameof(book_id), Name = "book_id")]
    public partial class book_author
    {
        [Key]
        [Column(TypeName = "int(11)")]
        public int id { get; set; }
        [Column(TypeName = "int(11)")]
        public int author_id { get; set; }
        [Column(TypeName = "int(11)")]
        public int book_id { get; set; }

        [ForeignKey(nameof(author_id))]
        [InverseProperty("book_author")]
        public virtual author author { get; set; }
        [ForeignKey(nameof(book_id))]
        [InverseProperty("book_author")]
        public virtual book book { get; set; }
    }
}
