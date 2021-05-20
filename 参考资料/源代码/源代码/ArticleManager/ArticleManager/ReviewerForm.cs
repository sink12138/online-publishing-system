using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ArticleManager
{
    public partial class ReviewerForm : Form
    {
        public ReviewerForm()
        {
            InitializeComponent();
        }

        private void AddAuthor_button_Click(object sender, EventArgs e)
        {
            AddReviewer_button.Enabled = false;
            AddReviewerForm arf = new AddReviewerForm();
            arf.ShowDialog();
            AddReviewer_button.Enabled = true;
            this.Close();
        }

        private void UpdateAuthor_button_Click(object sender, EventArgs e)
        {
            UpdateReviewer_button.Enabled = false;
            UpdateReviewerForm urf = new UpdateReviewerForm();
            urf.ShowDialog();
            UpdateReviewer_button.Enabled = true;
            this.Close();
        }

        private void ReviewerForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}
